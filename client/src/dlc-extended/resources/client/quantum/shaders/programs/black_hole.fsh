#version 410

#ifdef GL_ES
precision mediump float;
#endif

#define AA 2  //change to 1 to increase performance

#define _Speed 3.0  //disk rotation speed

#define _Steps  12. //disk texture layers
#define _Size 0.3 //size of BH

uniform vec3 iResolution;           // viewport resolution (in pixels)
uniform float iTime;                 // shader playback time (in seconds)
uniform float iTimeDelta;            // render time (in seconds)
uniform float iFrameRate;            // shader frame rate
uniform int iFrame;                // shader playback frame
//uniform float     iChannelTime[4];       // channel playback time (in seconds)
//uniform vec3      iChannelResolution[4]; // channel resolution (in pixels)
uniform vec4 iMouse;                // mouse pixel coords. xy: current (if MLB down), zw: click
uniform sampler2D iChannel0;          // input channel. XX = 2D/Cube
uniform sampler2D iChannel1;          // input channel. XX = 2D/Cube
uniform sampler2D iChannel2;          // input channel. XX = 2D/Cube
uniform sampler2D iChannel3;          // input channel. XX = 2D/Cube
uniform vec4 iDate;                 // (year, month, day, time in seconds)
uniform float iSampleRate;           // sound sample rate (i.e., 44100)
uniform vec3 iPosition;             // (x,y,z) in meters
uniform float iAngleX;
uniform float iAngleY;

in vec2 v_texCoords;

float hash(float x) { return fract(sin(x) * 152754.742);}
float hash(vec2 x) {    return hash(x.x + hash(x.y));}

float value(vec2 p, float f) //value noise
{
    float bl = hash(floor(p * f + vec2(0., 0.)));
    float br = hash(floor(p * f + vec2(1., 0.)));
    float tl = hash(floor(p * f + vec2(0., 1.)));
    float tr = hash(floor(p * f + vec2(1., 1.)));

    vec2 fr = fract(p * f);
    fr = (3. - 2. * fr) * fr * fr;
    float b = mix(bl, br, fr.x);
    float t = mix(tl, tr, fr.x);
    return mix(b, t, fr.y);
}

vec4 background(vec3 ray)
{
    vec2 uv = ray.xy;

    if (abs(ray.x) > 0.5)
    uv.x = ray.z;
    else if (abs(ray.y) > 0.5)
    uv.y = ray.z;


    float brightness = value(uv * 3., 100.); //(poor quality) "stars" created from value noise
    float color = value(uv * 2., 20.);
    brightness = pow(brightness, 256.);

    brightness = brightness * 100.;
    brightness = clamp(brightness, 0., 1.);

    vec3 stars = brightness * mix(vec3(1., .6, .2), vec3(.2, .6, 1), color);

    vec4 nebulae = texture(iChannel0, v_texCoords.xy * uv);
    return nebulae;
}

vec4 raymarchDisk(vec3 ray, vec3 zeroPos)
{
    //return vec4(1.,1.,1.,0.); //no disk

    vec3 position = zeroPos;
    float lengthPos = length(position.xz);
    float dist = min(1., lengthPos * (1. / _Size) * 0.5) * _Size * 0.4 * (1. / _Steps) / (abs(ray.y));

    position += dist * _Steps * ray * 0.5;

    vec2 deltaPos;
    deltaPos.x = -zeroPos.z * 0.01 + zeroPos.x;
    deltaPos.y = zeroPos.x * 0.01 + zeroPos.z;
    deltaPos = normalize(deltaPos - zeroPos.xz);

    float parallel = dot(ray.xz, deltaPos);
    parallel /= sqrt(lengthPos);
    parallel *= 0.5;
    float redShift = parallel + 0.3;
    redShift *= redShift;

    redShift = clamp(redShift, 0., 1.);

    float disMix = clamp((lengthPos - _Size * 2.) * (1. / _Size) * 0.24, 0., 1.);
    vec3 insideCol = mix(vec3(1.0, 0.8, 0.0), vec3(0.5, 0.13, 0.02) * 0.2, disMix);

    insideCol *= mix(vec3(0.4, 0.2, 0.1), vec3(1.6, 2.4, 4.0), redShift);
    insideCol *= 1.25;
    redShift += 0.12;
    redShift *= redShift;

    vec4 o = vec4(0.);

    for (float i = 0.; i < _Steps; i++)
    {
        position -= dist * ray;

        float intensity = clamp(1. - abs((i - 0.8) * (1. / _Steps) * 2.), 0., 1.);
        float lengthPos = length(position.xz);
        float distMult = 1.;

        distMult *= clamp((lengthPos - _Size * 0.75) * (1. / _Size) * 1.5, 0., 1.);
        distMult *= clamp((_Size * 10. - lengthPos) * (1. / _Size) * 0.20, 0., 1.);
        distMult *= distMult;

        float u = lengthPos + iTime * _Size * 0.3 + intensity * _Size * 0.2;

        vec2 xy;
        float rot = mod((2. * iAngleX) * 3.14, 8192.);
        xy.x = -position.z * sin(rot) + position.x * cos(rot);
        xy.y = position.x * sin(rot) + position.z * cos(rot);

        float x = abs(xy.x / (xy.y));
        float angle = 0.02 * atan(x);

        const float f = 70.;
        float noise = value(vec2(angle, u * (1. / _Size) * 0.05), f);
        noise = noise * 0.66 + 0.33 * value(vec2(angle, u * (1. / _Size) * 0.05), f * 2.);

        float extraWidth = noise * 1. * (1. - clamp(i * (1. / _Steps) * 2. - 1., 0., 1.));

        float alpha = clamp(noise * (intensity + extraWidth) * ((1. / _Size) * 10. + 0.01) * dist * distMult, 0., 1.);

        vec3 col = 2. * mix(vec3(0.3, 0.2, 0.15) * insideCol, insideCol, min(1., intensity * 2.));
        o = clamp(vec4(col * alpha + o.rgb * (1. - alpha), o.a * (1. - alpha) + alpha), vec4(0.), vec4(1.));

        lengthPos *= (1. / _Size);

        o.rgb += redShift * (intensity * 1. + 0.5) * (1. / _Steps) * 100. * distMult / (lengthPos * lengthPos);
    }

    o.rgb = clamp(o.rgb - 0.005, 0., 1.);
    return o;
}


void Rotate(inout vec3 vector, vec2 angle)
{
    vector.yz = cos(angle.y) * vector.yz
    + sin(angle.y) * vec2(-1, 1) * vector.zy;
    vector.xz = cos(angle.x) * vector.xz
    + sin(angle.x) * vec2(-1, 1) * vector.zx;
}

void mainImage(out vec4 colOut, in vec2 fragCoord)
{
    colOut = vec4(0.);;

    vec2 fragCoordRot;
    fragCoordRot.x = fragCoord.x * 0.985 + fragCoord.y * 0.174;
    fragCoordRot.y = fragCoord.y * 0.985 - fragCoord.x * 0.174;
    fragCoordRot += vec2(-0.06, 0.12) * iResolution.xy;

    for (int j = 0; j < AA; j++)
    for (int i = 0; i < AA; i++)
    {
        //setting up camera
        vec3 ray = normalize(vec3((fragCoordRot - iResolution.xy * .5 + vec2(i, j) / (float(AA))) / iResolution.x, 1));
        vec3 pos = iPosition;
        vec2 angle = vec2((2. * iAngleX) * 3.14, (2. * iAngleY) * 3.14);
        float dist = length(pos);
        Rotate(pos, angle);
        angle.xy -= min(.3 / dist, 3.14) * vec2(1, 0.5);
        Rotate(ray, angle);

        vec4 col = vec4(0.);
        vec4 glow = vec4(0.);
        vec4 outCol = vec4(100.);

        for (int disks = 0; disks < 20; disks++) //steps
        {

            for (int h = 0; h < 6; h++) //reduces tests for exit conditions (to minimise branching)
            {
                float dotpos = dot(pos, pos);
                float invDist = inversesqrt(dotpos); //1/distance to BH
                float centDist = dotpos * invDist;    //distance to BH
                float stepDist = 0.92 * abs(pos.y / (ray.y));  //conservative distance to disk (y==0)
                float farLimit = centDist * 0.5; //limit step size far from to BH
                float closeLimit = centDist * 0.1 + 0.05 * centDist * centDist * (1. / _Size); //limit step size closse to BH
                stepDist = min(stepDist, min(farLimit, closeLimit));

                float invDistSqr = invDist * invDist;
                float bendForce = stepDist * invDistSqr * _Size * 0.625;  //bending force
                ray = normalize(ray - (bendForce * invDist) * pos);  //bend ray towards BH
                pos += stepDist * ray;

                glow += vec4(1.2, 1.1, 1, 1.0) * (0.01 * stepDist * invDistSqr * invDistSqr * clamp(centDist * (2.) - 1.2, 0., 1.)); //adds fairly cheap glow
            }

            float dist2 = length(pos);

            if (dist2 < _Size * 0.1) //ray sucked in to BH
            {
                outCol = vec4(col.rgb * col.a + glow.rgb * (1. - col.a), 1.);
                break;
            }

            else if (dist2 > _Size * 1000.) //ray escaped BH
            {
                vec4 bg = background(ray);
                outCol = vec4(col.rgb * col.a + bg.rgb * (1. - col.a) + glow.rgb * (1. - col.a), 1.);
                break;
            }

            else if (abs(pos.y) <= _Size * 0.002) //ray hit accretion disk
            {
                vec4 diskCol = raymarchDisk(ray, pos);   //render disk
                pos.y = 0.;
                pos += abs(_Size * 0.001 / ray.y) * ray;
                col = vec4(diskCol.rgb * (1. - col.a) + col.rgb, col.a + diskCol.a * (1. - col.a));
            }
        }

        //if the ray never escaped or got sucked in
        if (outCol.r == 100.)
        outCol = vec4(col.rgb + glow.rgb * (col.a + glow.a), 1.);

        col = outCol;
        col.rgb = pow(col.rgb, vec3(0.6));

        colOut += col / float(AA * AA);
    }
}

out vec4 fragColor;
void main() {
    mainImage(fragColor, gl_FragCoord.xy);
}
