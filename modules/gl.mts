// noinspection JSUnusedGlobalSymbols

import IntBuffer from "@ultreon/quantumjs/jnio/IntBuffer.mjs";
import Gdx from "@ultreon/quantumjs/gdx/Gdx.mjs";
import Buffer from "@ultreon/quantumjs/jnio/Buffer.mjs";
import FloatBuffer from "@ultreon/quantumjs/jnio/FloatBuffer.mjs";

export class GL20 {
  public static readonly GL_ES_VERSION_2_0 = 1;
  public static readonly GL_DEPTH_BUFFER_BIT = 0x00000100;
  public static readonly GL_STENCIL_BUFFER_BIT = 0x00000400;
  public static readonly GL_COLOR_BUFFER_BIT = 0x00004000;
  public static readonly GL_FALSE = 0;
  public static readonly GL_TRUE = 1;
  public static readonly GL_POINTS = 0x0000;
  public static readonly GL_LINES = 0x0001;
  public static readonly GL_LINE_LOOP = 0x0002;
  public static readonly GL_LINE_STRIP = 0x0003;
  public static readonly GL_TRIANGLES = 0x0004;
  public static readonly GL_TRIANGLE_STRIP = 0x0005;
  public static readonly GL_TRIANGLE_FAN = 0x0006;
  public static readonly GL_ZERO = 0;
  public static readonly GL_ONE = 1;
  public static readonly GL_SRC_COLOR = 0x0300;
  public static readonly GL_ONE_MINUS_SRC_COLOR = 0x0301;
  public static readonly GL_SRC_ALPHA = 0x0302;
  public static readonly GL_ONE_MINUS_SRC_ALPHA = 0x0303;
  public static readonly GL_DST_ALPHA = 0x0304;
  public static readonly GL_ONE_MINUS_DST_ALPHA = 0x0305;
  public static readonly GL_DST_COLOR = 0x0306;
  public static readonly GL_ONE_MINUS_DST_COLOR = 0x0307;
  public static readonly GL_SRC_ALPHA_SATURATE = 0x0308;
  public static readonly GL_FUNC_ADD = 0x8006;
  public static readonly GL_BLEND_EQUATION = 0x8009;
  public static readonly GL_BLEND_EQUATION_RGB = 0x8009;
  public static readonly GL_BLEND_EQUATION_ALPHA = 0x883D;
  public static readonly GL_FUNC_SUBTRACT = 0x800A;
  public static readonly GL_FUNC_REVERSE_SUBTRACT = 0x800B;
  public static readonly GL_BLEND_DST_RGB = 0x80C8;
  public static readonly GL_BLEND_SRC_RGB = 0x80C9;
  public static readonly GL_BLEND_DST_ALPHA = 0x80CA;
  public static readonly GL_BLEND_SRC_ALPHA = 0x80CB;
  public static readonly GL_CONSTANT_COLOR = 0x8001;
  public static readonly GL_ONE_MINUS_CONSTANT_COLOR = 0x8002;
  public static readonly GL_CONSTANT_ALPHA = 0x8003;
  public static readonly GL_ONE_MINUS_CONSTANT_ALPHA = 0x8004;
  public static readonly GL_BLEND_COLOR = 0x8005;
  public static readonly GL_ARRAY_BUFFER = 0x8892;
  public static readonly GL_ELEMENT_ARRAY_BUFFER = 0x8893;
  public static readonly GL_ARRAY_BUFFER_BINDING = 0x8894;
  public static readonly GL_ELEMENT_ARRAY_BUFFER_BINDING = 0x8895;
  public static readonly GL_STREAM_DRAW = 0x88E0;
  public static readonly GL_STATIC_DRAW = 0x88E4;
  public static readonly GL_DYNAMIC_DRAW = 0x88E8;
  public static readonly GL_BUFFER_SIZE = 0x8764;
  public static readonly GL_BUFFER_USAGE = 0x8765;
  public static readonly GL_CURRENT_VERTEX_ATTRIB = 0x8626;
  public static readonly GL_FRONT = 0x0404;
  public static readonly GL_BACK = 0x0405;
  public static readonly GL_FRONT_AND_BACK = 0x0408;
  public static readonly GL_TEXTURE_2D = 0x0DE1;
  public static readonly GL_CULL_FACE = 0x0B44;
  public static readonly GL_BLEND = 0x0BE2;
  public static readonly GL_DITHER = 0x0BD0;
  public static readonly GL_STENCIL_TEST = 0x0B90;
  public static readonly GL_DEPTH_TEST = 0x0B71;
  public static readonly GL_SCISSOR_TEST = 0x0C11;
  public static readonly GL_POLYGON_OFFSET_FILL = 0x8037;
  public static readonly GL_SAMPLE_ALPHA_TO_COVERAGE = 0x809E;
  public static readonly GL_SAMPLE_COVERAGE = 0x80A0;
  public static readonly GL_NO_ERROR = 0;
  public static readonly GL_INVALID_ENUM = 0x0500;
  public static readonly GL_INVALID_VALUE = 0x0501;
  public static readonly GL_INVALID_OPERATION = 0x0502;
  public static readonly GL_OUT_OF_MEMORY = 0x0505;
  public static readonly GL_CW = 0x0900;
  public static readonly GL_CCW = 0x0901;
  public static readonly GL_LINE_WIDTH = 0x0B21;
  public static readonly GL_ALIASED_POINT_SIZE_RANGE = 0x846D;
  public static readonly GL_ALIASED_LINE_WIDTH_RANGE = 0x846E;
  public static readonly GL_CULL_FACE_MODE = 0x0B45;
  public static readonly GL_FRONT_FACE = 0x0B46;
  public static readonly GL_DEPTH_RANGE = 0x0B70;
  public static readonly GL_DEPTH_WRITEMASK = 0x0B72;
  public static readonly GL_DEPTH_CLEAR_VALUE = 0x0B73;
  public static readonly GL_DEPTH_FUNC = 0x0B74;
  public static readonly GL_STENCIL_CLEAR_VALUE = 0x0B91;
  public static readonly GL_STENCIL_FUNC = 0x0B92;
  public static readonly GL_STENCIL_FAIL = 0x0B94;
  public static readonly GL_STENCIL_PASS_DEPTH_FAIL = 0x0B95;
  public static readonly GL_STENCIL_PASS_DEPTH_PASS = 0x0B96;
  public static readonly GL_STENCIL_REF = 0x0B97;
  public static readonly GL_STENCIL_VALUE_MASK = 0x0B93;
  public static readonly GL_STENCIL_WRITEMASK = 0x0B98;
  public static readonly GL_STENCIL_BACK_FUNC = 0x8800;
  public static readonly GL_STENCIL_BACK_FAIL = 0x8801;
  public static readonly GL_STENCIL_BACK_PASS_DEPTH_FAIL = 0x8802;
  public static readonly GL_STENCIL_BACK_PASS_DEPTH_PASS = 0x8803;
  public static readonly GL_STENCIL_BACK_REF = 0x8CA3;
  public static readonly GL_STENCIL_BACK_VALUE_MASK = 0x8CA4;
  public static readonly GL_STENCIL_BACK_WRITEMASK = 0x8CA5;
  public static readonly GL_VIEWPORT = 0x0BA2;
  public static readonly GL_SCISSOR_BOX = 0x0C10;
  public static readonly GL_COLOR_CLEAR_VALUE = 0x0C22;
  public static readonly GL_COLOR_WRITEMASK = 0x0C23;
  public static readonly GL_UNPACK_ALIGNMENT = 0x0CF5;
  public static readonly GL_PACK_ALIGNMENT = 0x0D05;
  public static readonly GL_MAX_TEXTURE_SIZE = 0x0D33;
  public static readonly GL_MAX_TEXTURE_UNITS = 0x84E2;
  public static readonly GL_MAX_VIEWPORT_DIMS = 0x0D3A;
  public static readonly GL_SUBPIXEL_BITS = 0x0D50;
  public static readonly GL_RED_BITS = 0x0D52;
  public static readonly GL_GREEN_BITS = 0x0D53;
  public static readonly GL_BLUE_BITS = 0x0D54;
  public static readonly GL_ALPHA_BITS = 0x0D55;
  public static readonly GL_DEPTH_BITS = 0x0D56;
  public static readonly GL_STENCIL_BITS = 0x0D57;
  public static readonly GL_POLYGON_OFFSET_UNITS = 0x2A00;
  public static readonly GL_POLYGON_OFFSET_FACTOR = 0x8038;
  public static readonly GL_TEXTURE_BINDING_2D = 0x8069;
  public static readonly GL_SAMPLE_BUFFERS = 0x80A8;
  public static readonly GL_SAMPLES = 0x80A9;
  public static readonly GL_SAMPLE_COVERAGE_VALUE = 0x80AA;
  public static readonly GL_SAMPLE_COVERAGE_INVERT = 0x80AB;
  public static readonly GL_NUM_COMPRESSED_TEXTURE_FORMATS = 0x86A2;
  public static readonly GL_COMPRESSED_TEXTURE_FORMATS = 0x86A3;
  public static readonly GL_DONT_CARE = 0x1100;
  public static readonly GL_FASTEST = 0x1101;
  public static readonly GL_NICEST = 0x1102;
  public static readonly GL_GENERATE_MIPMAP = 0x8191;
  public static readonly GL_GENERATE_MIPMAP_HINT = 0x8192;
  public static readonly GL_BYTE = 0x1400;
  public static readonly GL_UNSIGNED_BYTE = 0x1401;
  public static readonly GL_SHORT = 0x1402;
  public static readonly GL_UNSIGNED_SHORT = 0x1403;
  public static readonly GL_INT = 0x1404;
  public static readonly GL_UNSIGNED_INT = 0x1405;
  public static readonly GL_FLOAT = 0x1406;
  public static readonly GL_FIXED = 0x140C;
  public static readonly GL_DEPTH_COMPONENT = 0x1902;
  public static readonly GL_ALPHA = 0x1906;
  public static readonly GL_RGB = 0x1907;
  public static readonly GL_RGBA = 0x1908;
  public static readonly GL_LUMINANCE = 0x1909;
  public static readonly GL_LUMINANCE_ALPHA = 0x190A;
  public static readonly GL_UNSIGNED_SHORT_4_4_4_4 = 0x8033;
  public static readonly GL_UNSIGNED_SHORT_5_5_5_1 = 0x8034;
  public static readonly GL_UNSIGNED_SHORT_5_6_5 = 0x8363;
  public static readonly GL_FRAGMENT_SHADER = 0x8B30;
  public static readonly GL_VERTEX_SHADER = 0x8B31;
  public static readonly GL_MAX_VERTEX_ATTRIBS = 0x8869;
  public static readonly GL_MAX_VERTEX_UNIFORM_VECTORS = 0x8DFB;
  public static readonly GL_MAX_VARYING_VECTORS = 0x8DFC;
  public static readonly GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS = 0x8B4D;
  public static readonly GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS = 0x8B4C;
  public static readonly GL_MAX_TEXTURE_IMAGE_UNITS = 0x8872;
  public static readonly GL_MAX_FRAGMENT_UNIFORM_VECTORS = 0x8DFD;
  public static readonly GL_SHADER_TYPE = 0x8B4F;
  public static readonly GL_DELETE_STATUS = 0x8B80;
  public static readonly GL_LINK_STATUS = 0x8B82;
  public static readonly GL_VALIDATE_STATUS = 0x8B83;
  public static readonly GL_ATTACHED_SHADERS = 0x8B85;
  public static readonly GL_ACTIVE_UNIFORMS = 0x8B86;
  public static readonly GL_ACTIVE_UNIFORM_MAX_LENGTH = 0x8B87;
  public static readonly GL_ACTIVE_ATTRIBUTES = 0x8B89;
  public static readonly GL_ACTIVE_ATTRIBUTE_MAX_LENGTH = 0x8B8A;
  public static readonly GL_SHADING_LANGUAGE_VERSION = 0x8B8C;
  public static readonly GL_CURRENT_PROGRAM = 0x8B8D;
  public static readonly GL_NEVER = 0x0200;
  public static readonly GL_LESS = 0x0201;
  public static readonly GL_EQUAL = 0x0202;
  public static readonly GL_LEQUAL = 0x0203;
  public static readonly GL_GREATER = 0x0204;
  public static readonly GL_NOTEQUAL = 0x0205;
  public static readonly GL_GEQUAL = 0x0206;
  public static readonly GL_ALWAYS = 0x0207;
  public static readonly GL_KEEP = 0x1E00;
  public static readonly GL_REPLACE = 0x1E01;
  public static readonly GL_INCR = 0x1E02;
  public static readonly GL_DECR = 0x1E03;
  public static readonly GL_INVERT = 0x150A;
  public static readonly GL_INCR_WRAP = 0x8507;
  public static readonly GL_DECR_WRAP = 0x8508;
  public static readonly GL_VENDOR = 0x1F00;
  public static readonly GL_RENDERER = 0x1F01;
  public static readonly GL_VERSION = 0x1F02;
  public static readonly GL_EXTENSIONS = 0x1F03;
  public static readonly GL_NEAREST = 0x2600;
  public static readonly GL_LINEAR = 0x2601;
  public static readonly GL_NEAREST_MIPMAP_NEAREST = 0x2700;
  public static readonly GL_LINEAR_MIPMAP_NEAREST = 0x2701;
  public static readonly GL_NEAREST_MIPMAP_LINEAR = 0x2702;
  public static readonly GL_LINEAR_MIPMAP_LINEAR = 0x2703;
  public static readonly GL_TEXTURE_MAG_FILTER = 0x2800;
  public static readonly GL_TEXTURE_MIN_FILTER = 0x2801;
  public static readonly GL_TEXTURE_WRAP_S = 0x2802;
  public static readonly GL_TEXTURE_WRAP_T = 0x2803;
  public static readonly GL_TEXTURE = 0x1702;
  public static readonly GL_TEXTURE_CUBE_MAP = 0x8513;
  public static readonly GL_TEXTURE_BINDING_CUBE_MAP = 0x8514;
  public static readonly GL_TEXTURE_CUBE_MAP_POSITIVE_X = 0x8515;
  public static readonly GL_TEXTURE_CUBE_MAP_NEGATIVE_X = 0x8516;
  public static readonly GL_TEXTURE_CUBE_MAP_POSITIVE_Y = 0x8517;
  public static readonly GL_TEXTURE_CUBE_MAP_NEGATIVE_Y = 0x8518;
  public static readonly GL_TEXTURE_CUBE_MAP_POSITIVE_Z = 0x8519;
  public static readonly GL_TEXTURE_CUBE_MAP_NEGATIVE_Z = 0x851A;
  public static readonly GL_MAX_CUBE_MAP_TEXTURE_SIZE = 0x851C;
  public static readonly GL_TEXTURE0 = 0x84C0;
  public static readonly GL_TEXTURE1 = 0x84C1;
  public static readonly GL_TEXTURE2 = 0x84C2;
  public static readonly GL_TEXTURE3 = 0x84C3;
  public static readonly GL_TEXTURE4 = 0x84C4;
  public static readonly GL_TEXTURE5 = 0x84C5;
  public static readonly GL_TEXTURE6 = 0x84C6;
  public static readonly GL_TEXTURE7 = 0x84C7;
  public static readonly GL_TEXTURE8 = 0x84C8;
  public static readonly GL_TEXTURE9 = 0x84C9;
  public static readonly GL_TEXTURE10 = 0x84CA;
  public static readonly GL_TEXTURE11 = 0x84CB;
  public static readonly GL_TEXTURE12 = 0x84CC;
  public static readonly GL_TEXTURE13 = 0x84CD;
  public static readonly GL_TEXTURE14 = 0x84CE;
  public static readonly GL_TEXTURE15 = 0x84CF;
  public static readonly GL_TEXTURE16 = 0x84D0;
  public static readonly GL_TEXTURE17 = 0x84D1;
  public static readonly GL_TEXTURE18 = 0x84D2;
  public static readonly GL_TEXTURE19 = 0x84D3;
  public static readonly GL_TEXTURE20 = 0x84D4;
  public static readonly GL_TEXTURE21 = 0x84D5;
  public static readonly GL_TEXTURE22 = 0x84D6;
  public static readonly GL_TEXTURE23 = 0x84D7;
  public static readonly GL_TEXTURE24 = 0x84D8;
  public static readonly GL_TEXTURE25 = 0x84D9;
  public static readonly GL_TEXTURE26 = 0x84DA;
  public static readonly GL_TEXTURE27 = 0x84DB;
  public static readonly GL_TEXTURE28 = 0x84DC;
  public static readonly GL_TEXTURE29 = 0x84DD;
  public static readonly GL_TEXTURE30 = 0x84DE;
  public static readonly GL_TEXTURE31 = 0x84DF;
  public static readonly GL_ACTIVE_TEXTURE = 0x84E0;
  public static readonly GL_REPEAT = 0x2901;
  public static readonly GL_CLAMP_TO_EDGE = 0x812F;
  public static readonly GL_MIRRORED_REPEAT = 0x8370;
  public static readonly GL_FLOAT_VEC2 = 0x8B50;
  public static readonly GL_FLOAT_VEC3 = 0x8B51;
  public static readonly GL_FLOAT_VEC4 = 0x8B52;
  public static readonly GL_INT_VEC2 = 0x8B53;
  public static readonly GL_INT_VEC3 = 0x8B54;
  public static readonly GL_INT_VEC4 = 0x8B55;
  public static readonly GL_BOOL = 0x8B56;
  public static readonly GL_BOOL_VEC2 = 0x8B57;
  public static readonly GL_BOOL_VEC3 = 0x8B58;
  public static readonly GL_BOOL_VEC4 = 0x8B59;
  public static readonly GL_FLOAT_MAT2 = 0x8B5A;
  public static readonly GL_FLOAT_MAT3 = 0x8B5B;
  public static readonly GL_FLOAT_MAT4 = 0x8B5C;
  public static readonly GL_SAMPLER_2D = 0x8B5E;
  public static readonly GL_SAMPLER_CUBE = 0x8B60;
  public static readonly GL_VERTEX_ATTRIB_ARRAY_ENABLED = 0x8622;
  public static readonly GL_VERTEX_ATTRIB_ARRAY_SIZE = 0x8623;
  public static readonly GL_VERTEX_ATTRIB_ARRAY_STRIDE = 0x8624;
  public static readonly GL_VERTEX_ATTRIB_ARRAY_TYPE = 0x8625;
  public static readonly GL_VERTEX_ATTRIB_ARRAY_NORMALIZED = 0x886A;
  public static readonly GL_VERTEX_ATTRIB_ARRAY_POINTER = 0x8645;
  public static readonly GL_VERTEX_ATTRIB_ARRAY_BUFFER_BINDING = 0x889F;
  public static readonly GL_IMPLEMENTATION_COLOR_READ_TYPE = 0x8B9A;
  public static readonly GL_IMPLEMENTATION_COLOR_READ_FORMAT = 0x8B9B;
  public static readonly GL_COMPILE_STATUS = 0x8B81;
  public static readonly GL_INFO_LOG_LENGTH = 0x8B84;
  public static readonly GL_SHADER_SOURCE_LENGTH = 0x8B88;
  public static readonly GL_SHADER_COMPILER = 0x8DFA;
  public static readonly GL_SHADER_BINARY_FORMATS = 0x8DF8;
  public static readonly GL_NUM_SHADER_BINARY_FORMATS = 0x8DF9;
  public static readonly GL_LOW_FLOAT = 0x8DF0;
  public static readonly GL_MEDIUM_FLOAT = 0x8DF1;
  public static readonly GL_HIGH_FLOAT = 0x8DF2;
  public static readonly GL_LOW_INT = 0x8DF3;
  public static readonly GL_MEDIUM_INT = 0x8DF4;
  public static readonly GL_HIGH_INT = 0x8DF5;
  public static readonly GL_FRAMEBUFFER = 0x8D40;
  public static readonly GL_RENDERBUFFER = 0x8D41;
  public static readonly GL_RGBA4 = 0x8056;
  public static readonly GL_RGB5_A1 = 0x8057;
  public static readonly GL_RGB565 = 0x8D62;
  public static readonly GL_DEPTH_COMPONENT16 = 0x81A5;
  public static readonly GL_STENCIL_INDEX = 0x1901;
  public static readonly GL_STENCIL_INDEX8 = 0x8D48;
  public static readonly GL_RENDERBUFFER_WIDTH = 0x8D42;
  public static readonly GL_RENDERBUFFER_HEIGHT = 0x8D43;
  public static readonly GL_RENDERBUFFER_INTERNAL_FORMAT = 0x8D44;
  public static readonly GL_RENDERBUFFER_RED_SIZE = 0x8D50;
  public static readonly GL_RENDERBUFFER_GREEN_SIZE = 0x8D51;
  public static readonly GL_RENDERBUFFER_BLUE_SIZE = 0x8D52;
  public static readonly GL_RENDERBUFFER_ALPHA_SIZE = 0x8D53;
  public static readonly GL_RENDERBUFFER_DEPTH_SIZE = 0x8D54;
  public static readonly GL_RENDERBUFFER_STENCIL_SIZE = 0x8D55;
  public static readonly GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE = 0x8CD0;
  public static readonly GL_FRAMEBUFFER_ATTACHMENT_OBJECT_NAME = 0x8CD1;
  public static readonly GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL = 0x8CD2;
  public static readonly GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_CUBE_MAP_FACE = 0x8CD3;
  public static readonly GL_COLOR_ATTACHMENT0 = 0x8CE0;
  public static readonly GL_DEPTH_ATTACHMENT = 0x8D00;
  public static readonly GL_STENCIL_ATTACHMENT = 0x8D20;
  public static readonly GL_NONE = 0;
  public static readonly GL_FRAMEBUFFER_COMPLETE = 0x8CD5;
  public static readonly GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT = 0x8CD6;
  public static readonly GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT = 0x8CD7;
  public static readonly GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS = 0x8CD9;
  public static readonly GL_FRAMEBUFFER_UNSUPPORTED = 0x8CDD;
  public static readonly GL_FRAMEBUFFER_BINDING = 0x8CA6;
  public static readonly GL_RENDERBUFFER_BINDING = 0x8CA7;
  public static readonly GL_MAX_RENDERBUFFER_SIZE = 0x84E8;
  public static readonly GL_INVALID_FRAMEBUFFER_OPERATION = 0x0506;
  public static readonly GL_VERTEX_PROGRAM_POINT_SIZE = 0x8642;

  // Extensions
  public static readonly GL_COVERAGE_BUFFER_BIT_NV = 0x8000;
  public static readonly GL_TEXTURE_MAX_ANISOTROPY_EXT = 0x84FE;
  public static readonly GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT = 0x84FF;

  // Functions
  public static glActiveTexture (texture: number) { Gdx.gl.glActiveTexture(texture) };
  public static glBindTexture (target: number, texture: number) { Gdx.gl.glBindTexture(target, texture) };
  public static glBlendFunc (sfactor: number, dfactor: number) { Gdx.gl.glBlendFunc(sfactor, dfactor) };

  public static glClear (mask: number) { Gdx.gl.glClear(mask) };
  public static glClearColor (red: number, green: number, blue: number, alpha: number) { Gdx.gl.glClearColor(red, green, blue, alpha) };
  public static glClearDepthf (depth: number) { Gdx.gl.glClearDepthf(depth) };
  public static glClearStencil (s: number) { Gdx.gl.glClearStencil(s) };
  public static glColorMask (red: boolean, green: boolean, blue: boolean, alpha: boolean) { Gdx.gl.glColorMask(red, green, blue, alpha) };
  public static glCompressedTexImage2D (target: number, level: number, internalformat: number, width: number, height: number, border: number, imageSize: number, data: Buffer) { Gdx.gl.glCompressedTexImage2D(target, level, internalformat, width, height, border, imageSize, data) };
  public static glCompressedTexSubImage2D (target: number, level: number, xoffset: number, yoffset: number, width: number, height: number, format: number, imageSize: number, data: Buffer) { Gdx.gl.glCompressedTexSubImage2D(target, level, xoffset, yoffset, width, height, format, imageSize, data) };
  public static glCopyTexImage2D (target: number, level: number, internalformat: number, x: number, y: number, width: number, height: number, border: number) { Gdx.gl.glCopyTexImage2D(target, level, internalformat, x, y, width, height, border) };
  public static glCopyTexSubImage2D (target: number, level: number, xoffset: number, yoffset: number, x: number, y: number, width: number, height: number) { Gdx.gl.glCopyTexSubImage2D(target, level, xoffset, yoffset, x, y, width, height) };
  public static glCullFace (mode: number) { Gdx.gl.glCullFace(mode) };
  public static glDeleteTextures (n: number, textures: IntBuffer) { Gdx.gl.glDeleteTextures(n, textures) };
  public static glDeleteTexture (texture: number) { Gdx.gl.glDeleteTexture(texture) };
  public static glDepthFunc (func: number) { Gdx.gl.glDepthFunc(func) };
  public static glDepthMask (flag: boolean) { Gdx.gl.glDepthMask(flag) };
  public static glDepthRangef (zNear: number, zFar: number) { Gdx.gl.glDepthRangef(zNear, zFar) };
  public static glDisable (cap: number) { Gdx.gl.glDisable(cap) };
  public static glDrawArrays (mode: number, first: number, count: number) { Gdx.gl.glDrawArrays(mode, first, count) };
  public static glDrawElements (mode: number, count: number, type: number, indices: number | Buffer) { Gdx.gl.glDrawElements(mode, count, type, indices as any) };
  public static glEnable (cap: number) { Gdx.gl.glEnable(cap) };
  public static glFinish () { Gdx.gl.glFinish() };
  public static glFlush () { Gdx.gl.glFlush() };
  public static glFrontFace (mode: number) { Gdx.gl.glFrontFace(mode) };
  public static glGenTextures (n: number, textures: IntBuffer) { Gdx.gl.glGenTextures(n, textures) };
  public static glGenTexture (): number { return Gdx.gl.glGenTexture() };
  public static glGetError (): number { return Gdx.gl.glGetError() };
  public static glGetIntegerv (pname: number, params: IntBuffer) { Gdx.gl.glGetIntegerv(pname, params as any) };
  public static glGetString (name: number): string { return Gdx.gl.glGetString(name) };
  public static glHint (target: number, mode: number) { Gdx.gl.glHint(target, mode) };
  public static glLineWidth (width: number) { Gdx.gl.glLineWidth(width) };
  public static glPixelStorei (pname: number, param: number) { Gdx.gl.glPixelStorei(pname, param) };
  public static glPolygonOffset (factor: number, units: number) { Gdx.gl.glPolygonOffset(factor, units) };
  public static glReadPixels (x: number, y: number, width: number, height: number, format: number, type: number, pixels: Buffer) { Gdx.gl.glReadPixels(x, y, width, height, format, type, pixels) };
  public static glScissor (x: number, y: number, width: number, height: number) { Gdx.gl.glScissor(x, y, width, height) };

  public static glStencilFunc (func: number, ref: number, mask: number) { Gdx.gl.glStencilFunc(func, ref, mask) };
  public static glStencilMask (mask: number) { Gdx.gl.glStencilMask(mask) };
  public static glStencilOp (fail: number, zfail: number, zpass: number) { Gdx.gl.glStencilOp(fail, zfail, zpass) };
  public static glTexImage2D (target: number, level: number, internalformat: number, width: number, height: number, border: number, format: number, type: number, pixels: Buffer) { Gdx.gl.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels) };
  public static glTexParameterf (target: number, pname: number, param: number) { Gdx.gl.glTexParameterf(target, pname, param) };
  public static glTexSubImage2D (target: number, level: number, xoffset: number, yoffset: number, width: number, height: number, format: number, type: number, pixels: Buffer) { Gdx.gl.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels) };
  public static glViewport (x: number, y: number, width: number, height: number) { Gdx.gl.glViewport(x, y, width, height) };

  public static glAttachShader (program: number, shader: number) { Gdx.gl.glAttachShader(program, shader) };
  public static glBindAttribLocation (program: number, index: number, name: string) { Gdx.gl.glBindAttribLocation(program, index, name) };
  public static glBindBuffer (target: number, buffer: number) { Gdx.gl.glBindBuffer(target, buffer) };
  public static glBindFramebuffer (target: number, framebuffer: number) { Gdx.gl.glBindFramebuffer(target, framebuffer) };
  public static glBindRenderbuffer (target: number, renderbuffer: number) { Gdx.gl.glBindRenderbuffer(target, renderbuffer) };
  public static glBlendColor (red: number, green: number, blue: number, alpha: number) { Gdx.gl.glBlendColor(red, green, blue, alpha) };
  public static glBlendEquation (mode: number) { Gdx.gl.glBlendEquation(mode) };
  public static glBlendEquationSeparate (modeRGB: number, modeAlpha: number) { Gdx.gl.glBlendEquationSeparate(modeRGB, modeAlpha) };
  public static glBlendFuncSeparate (srcRGB: number, dstRGB: number, srcAlpha: number, dstAlpha: number) { Gdx.gl.glBlendFuncSeparate(srcRGB, dstRGB, srcAlpha, dstAlpha) };
  public static glBufferData (target: number, size: number, data: Buffer, usage: number) { Gdx.gl.glBufferData(target, size, data, usage) };
  public static glBufferSubData (target: number, offset: number, size: number, data: Buffer) { Gdx.gl.glBufferSubData(target, offset, size, data) };

  public static glCheckFramebufferStatus (target: number): number { return Gdx.gl.glCheckFramebufferStatus(target) };
  public static glCompileShader (shader: number) { Gdx.gl.glCompileShader(shader) };
  public static glCreateProgram (): number { return Gdx.gl.glCreateProgram() };
  public static glCreateShader (type: number): number { return Gdx.gl.glCreateShader(type) };
  public static glDeleteBuffer (buffer: number) { Gdx.gl.glDeleteBuffer(buffer) };
  public static glDeleteBuffers (n: number, buffers: IntBuffer) { Gdx.gl.glDeleteBuffers(n, buffers) };
  public static glDeleteFramebuffer (framebuffer: number) { Gdx.gl.glDeleteFramebuffer(framebuffer) };
  public static glDeleteFramebuffers (n: number, framebuffers: IntBuffer) { Gdx.gl.glDeleteFramebuffers(n, framebuffers) };
  public static glDeleteProgram (program: number) { Gdx.gl.glDeleteProgram(program) };
  public static glDeleteRenderbuffer (renderbuffer: number) { Gdx.gl.glDeleteRenderbuffer(renderbuffer) };
  public static glDeleteRenderbuffers (n: number, renderbuffers: IntBuffer) { Gdx.gl.glDeleteRenderbuffers(n, renderbuffers) };
  public static glDeleteShader (shader: number) { Gdx.gl.glDeleteShader(shader) };
  public static glDetachShader (program: number, shader: number) { Gdx.gl.glDetachShader(program, shader) };
  public static glDisableVertexAttribArray (index: number) { Gdx.gl.glDisableVertexAttribArray(index) };
  public static glEnableVertexAttribArray (index: number) { Gdx.gl.glEnableVertexAttribArray(index) };
  public static glFramebufferRenderbuffer (target: number, attachment: number, renderbuffertarget: number, renderbuffer: number) { Gdx.gl.glFramebufferRenderbuffer(target, attachment, renderbuffertarget, renderbuffer) };
  public static glFramebufferTexture2D (target: number, attachment: number, textarget: number, texture: number, level: number) { Gdx.gl.glFramebufferTexture2D(target, attachment, textarget, texture, level) };
  public static glGenBuffer (): number { return Gdx.gl.glGenBuffer() };
  public static glGenBuffers (n: number, buffers: IntBuffer) { Gdx.gl.glGenBuffers(n, buffers) };
  public static glGenerateMipmap (target: number) { Gdx.gl.glGenerateMipmap(target) };
  public static glGenFramebuffer (): number { return Gdx.gl.glGenFramebuffer() };
  public static glGenFramebuffers (n: number, framebuffers: IntBuffer) { Gdx.gl.glGenFramebuffers(n, framebuffers) };
  public static glGenRenderbuffer (): number { return Gdx.gl.glGenRenderbuffer() };
  public static glGenRenderbuffers (n: number, renderbuffers: IntBuffer) { Gdx.gl.glGenRenderbuffers(n, renderbuffers) };
  public static glGetActiveAttrib (program: number, index: number, size: IntBuffer,type: IntBuffer): string { return Gdx.gl.glGetActiveAttrib(program, index, size, type) };
  public static glGetActiveUniform (program: number, index: number, size: IntBuffer,type: IntBuffer): string { return Gdx.gl.glGetActiveUniform(program, index, size, type) };
  public static glGetAttachedShaders (program: number, maxcount: number, count: Buffer, shaders: IntBuffer) { Gdx.gl.glGetAttachedShaders(program, maxcount, count, shaders) };
  public static glGetAttribLocation (program: number, name: string): number { return Gdx.gl.glGetAttribLocation(program, name) };
  public static glGetBooleanv (pname: number, params: Buffer) { Gdx.gl.glGetBooleanv(pname, params as any) };
  public static glGetBufferParameteriv (target: number, pname: number, params: IntBuffer) { Gdx.gl.glGetBufferParameteriv(target, pname, params as any) };
  public static glGetFloatv (pname: number, params: FloatBuffer) { Gdx.gl.glGetFloatv(pname, params as any) };
  public static glGetFramebufferAttachmentParameteriv (target: number, attachment: number, pname: number, params: IntBuffer) { Gdx.gl.glGetFramebufferAttachmentParameteriv(target, attachment, pname, params as any) };
  public static glGetProgramiv (program: number, pname: number, params: IntBuffer) { Gdx.gl.glGetProgramiv(program, pname, params as any) };
  public static glGetProgramInfoLog (program: number): string { return Gdx.gl.glGetProgramInfoLog(program) };
  public static glGetRenderbufferParameteriv (target: number, pname: number, params: IntBuffer) { Gdx.gl.glGetRenderbufferParameteriv(target, pname, params as any) };
  public static glGetShaderiv (shader: number, pname: number, params: IntBuffer) { Gdx.gl.glGetShaderiv(shader, pname, params as any) };
  public static glGetShaderInfoLog (shader: number): string { return Gdx.gl.glGetShaderInfoLog(shader) };
  public static glGetShaderPrecisionFormat (shadertype: number, precisiontype: number, range: IntBuffer,precision: IntBuffer) { Gdx.gl.glGetShaderPrecisionFormat(shadertype, precisiontype, range, precision) };
  public static glGetTexParameterfv (target: number, pname: number, params: FloatBuffer) { Gdx.gl.glGetTexParameterfv(target, pname, params as any) };
  public static glGetTexParameteriv (target: number, pname: number, params: IntBuffer) { Gdx.gl.glGetTexParameteriv(target, pname, params as any) };
  public static glGetUniformfv (program: number, location: number, params: FloatBuffer) { Gdx.gl.glGetUniformfv(program, location, params as any) };
  public static glGetUniformiv (program: number, location: number, params: IntBuffer) { Gdx.gl.glGetUniformiv(program, location, params as any) };
  public static glGetUniformLocation (program: number, name: string): number { return Gdx.gl.glGetUniformLocation(program, name) };
  public static glGetVertexAttribfv (index: number, pname: number, params: FloatBuffer) { Gdx.gl.glGetVertexAttribfv(index, pname, params as any) };
  public static glGetVertexAttribiv (index: number, pname: number, params: IntBuffer) { Gdx.gl.glGetVertexAttribiv(index, pname, params as any) };
  public static glGetVertexAttribPointerv (index: number, pname: number, pointer: Buffer) { Gdx.gl.glGetVertexAttribPointerv(index, pname, pointer) };
  public static glIsBuffer (buffer: number): boolean { return Gdx.gl.glIsBuffer(buffer) };
  public static glIsEnabled (cap: number): boolean { return Gdx.gl.glIsEnabled(cap) };
  public static glIsFramebuffer (framebuffer: number): boolean { return Gdx.gl.glIsFramebuffer(framebuffer) };
  public static glIsProgram (program: number): boolean { return Gdx.gl.glIsProgram(program) };
  public static glIsRenderbuffer (renderbuffer: number): boolean { return Gdx.gl.glIsRenderbuffer(renderbuffer) };

  public static glIsShader (shader: number): boolean { return Gdx.gl.glIsShader(shader) };
  public static glIsTexture (texture: number): boolean { return Gdx.gl.glIsTexture(texture) };
  public static glLinkProgram (program: number) { Gdx.gl.glLinkProgram(program) };
  public static glReleaseShaderCompiler () { Gdx.gl.glReleaseShaderCompiler() };
  public static glRenderbufferStorage (target: number, internalformat: number, width: number, height: number) { Gdx.gl.glRenderbufferStorage(target, internalformat, width, height) };
  public static glSampleCoverage (value: number, invert: boolean) { Gdx.gl.glSampleCoverage(value, invert) };
  public static glShaderBinary (n: number, shaders: IntBuffer,binaryformat: number, binary: Buffer, length: number) { Gdx.gl.glShaderBinary(n, shaders, binaryformat, binary, length) };
  public static glShaderSource (shader: number, string: string) { Gdx.gl.glShaderSource(shader, string) };
  public static glStencilFuncSeparate (face: number, func: number, ref: number, mask: number) { Gdx.gl.glStencilFuncSeparate(face, func, ref, mask) };
  public static glStencilMaskSeparate (face: number, mask: number) { Gdx.gl.glStencilMaskSeparate(face, mask) };
  public static glStencilOpSeparate (face: number, fail: number, zfail: number, zpass: number) { Gdx.gl.glStencilOpSeparate(face, fail, zfail, zpass) };
  public static glTexParameteri (target: number, pname: number, param: number) { Gdx.gl.glTexParameteri(target, pname, param) };
  public static glTexParameteriv (target: number, pname: number, params: IntBuffer) { Gdx.gl.glTexParameteriv(target, pname, params as any) };
  public static glUniform1f (location: number, x: number) { Gdx.gl.glUniform1f(location, x) };
  public static glUniform1fv (location: number, count: number, v: number[], offset: number) { Gdx.gl.glUniform1fv(location, count, v as any, offset) };
  public static glUniform1i (location: number, x: number) { Gdx.gl.glUniform1i(location, x) };
  public static glUniform1iv (location: number, count: number, v: number[], offset: number) { Gdx.gl.glUniform1iv(location, count, v as any, offset) };
  public static glUniform2f (location: number, x: number, y: number) { Gdx.gl.glUniform2f(location, x, y) };
  public static glUniform2fv (location: number, count: number, v: number[], offset: number) { Gdx.gl.glUniform2fv(location, count, v as any, offset) };
  public static glUniform2i (location: number, x: number, y: number) { Gdx.gl.glUniform2i(location, x, y) };
  public static glUniform2iv (location: number, count: number, v: number[], offset: number) { Gdx.gl.glUniform2iv(location, count, v as any, offset) };
  public static glUniform3f (location: number, x: number, y: number, z: number) { Gdx.gl.glUniform3f(location, x, y, z) };
  public static glUniform3fv (location: number, count: number, v: number[], offset: number) { Gdx.gl.glUniform3fv(location, count, v as any, offset) };
  public static glUniform3i (location: number, x: number, y: number, z: number) { Gdx.gl.glUniform3i(location, x, y, z) };
  public static glUniform3iv (location: number, count: number, v: number[], offset: number) { Gdx.gl.glUniform3iv(location, count, v as any, offset) };
  public static glUniform4f (location: number, x: number, y: number, z: number, w: number) { Gdx.gl.glUniform4f(location, x, y, z, w) };
  public static glUniform4fv (location: number, count: number, v: number[], offset: number) { Gdx.gl.glUniform4fv(location, count, v as any, offset) };
  public static glUniform4i (location: number, x: number, y: number, z: number, w: number) { Gdx.gl.glUniform4i(location, x, y, z, w) };
  public static glUniform4iv (location: number, count: number, v: number[], offset: number) { Gdx.gl.glUniform4iv(location, count, v as any, offset) };
  public static glUniformMatrix2fv (location: number, count: number, transpose: boolean, value: number[], offset: number) { Gdx.gl.glUniformMatrix2fv(location, count, transpose, value as any, offset) };
  public static glUniformMatrix3fv (location: number, count: number, transpose: boolean, value: number[], offset: number) { Gdx.gl.glUniformMatrix3fv(location, count, transpose, value as any, offset) };
  public static glUniformMatrix4fv (location: number, count: number, transpose: boolean, value: number[], offset: number) { Gdx.gl.glUniformMatrix4fv(location, count, transpose, value as any, offset) };
  public static glUseProgram (program: number) { Gdx.gl.glUseProgram(program) };
  public static glValidateProgram (program: number) { Gdx.gl.glValidateProgram(program) };
  public static glVertexAttrib1f (indx: number, x: number) { Gdx.gl.glVertexAttrib1f(indx, x) };
  public static glVertexAttrib2f (indx: number, x: number, y: number) { Gdx.gl.glVertexAttrib2f(indx, x, y) };
  public static glVertexAttrib3f (indx: number, x: number, y: number, z: number) { Gdx.gl.glVertexAttrib3f(indx, x, y, z) };
  public static glVertexAttrib4f (indx: number, x: number, y: number, z: number, w: number) { Gdx.gl.glVertexAttrib4f(indx, x, y, z, w) };
}

export class GL30 extends GL20 {
	public static readonly GL_READ_BUFFER: number = 0x0C02;
	public static readonly GL_UNPACK_ROW_LENGTH: number = 0x0CF2;
	public static readonly GL_UNPACK_SKIP_ROWS: number = 0x0CF3;
	public static readonly GL_UNPACK_SKIP_PIXELS: number = 0x0CF4;
	public static readonly GL_PACK_ROW_LENGTH: number = 0x0D02;
	public static readonly GL_PACK_SKIP_ROWS: number = 0x0D03;
	public static readonly GL_PACK_SKIP_PIXELS: number = 0x0D04;
	public static readonly GL_COLOR: number = 0x1800;
	public static readonly GL_DEPTH: number = 0x1801;
	public static readonly GL_STENCIL: number = 0x1802;
	public static readonly GL_RED: number = 0x1903;
	public static readonly GL_RGB8: number = 0x8051;
	public static readonly GL_RGBA8: number = 0x8058;
	public static readonly GL_RGB10_A2: number = 0x8059;
	public static readonly GL_TEXTURE_BINDING_3D: number = 0x806A;
	public static readonly GL_UNPACK_SKIP_IMAGES: number = 0x806D;
	public static readonly GL_UNPACK_IMAGE_HEIGHT: number = 0x806E;
	public static readonly GL_TEXTURE_3D: number = 0x806F;
	public static readonly GL_TEXTURE_WRAP_R: number = 0x8072;
	public static readonly GL_MAX_3D_TEXTURE_SIZE: number = 0x8073;
	public static readonly GL_UNSIGNED_INT_2_10_10_10_REV: number = 0x8368;
	public static readonly GL_MAX_ELEMENTS_VERTICES: number = 0x80E8;
	public static readonly GL_MAX_ELEMENTS_INDICES: number = 0x80E9;
	public static readonly GL_TEXTURE_MIN_LOD: number = 0x813A;
	public static readonly GL_TEXTURE_MAX_LOD: number = 0x813B;
	public static readonly GL_TEXTURE_BASE_LEVEL: number = 0x813C;
	public static readonly GL_TEXTURE_MAX_LEVEL: number = 0x813D;
	public static readonly GL_MIN: number = 0x8007;
	public static readonly GL_MAX: number = 0x8008;
	public static readonly GL_DEPTH_COMPONENT24: number = 0x81A6;
	public static readonly GL_MAX_TEXTURE_LOD_BIAS: number = 0x84FD;
	public static readonly GL_TEXTURE_COMPARE_MODE: number = 0x884C;
	public static readonly GL_TEXTURE_COMPARE_FUNC: number = 0x884D;
	public static readonly GL_CURRENT_QUERY: number = 0x8865;
	public static readonly GL_QUERY_RESULT: number = 0x8866;
	public static readonly GL_QUERY_RESULT_AVAILABLE: number = 0x8867;
	public static readonly GL_BUFFER_MAPPED: number = 0x88BC;
	public static readonly GL_BUFFER_MAP_POINTER: number = 0x88BD;
	public static readonly GL_STREAM_READ: number = 0x88E1;
	public static readonly GL_STREAM_COPY: number = 0x88E2;
	public static readonly GL_STATIC_READ: number = 0x88E5;
	public static readonly GL_STATIC_COPY: number = 0x88E6;
	public static readonly GL_DYNAMIC_READ: number = 0x88E9;
	public static readonly GL_DYNAMIC_COPY: number = 0x88EA;
	public static readonly GL_MAX_DRAW_BUFFERS: number = 0x8824;
	public static readonly GL_DRAW_BUFFER0: number = 0x8825;
	public static readonly GL_DRAW_BUFFER1: number = 0x8826;
	public static readonly GL_DRAW_BUFFER2: number = 0x8827;
	public static readonly GL_DRAW_BUFFER3: number = 0x8828;
	public static readonly GL_DRAW_BUFFER4: number = 0x8829;
	public static readonly GL_DRAW_BUFFER5: number = 0x882A;
	public static readonly GL_DRAW_BUFFER6: number = 0x882B;
	public static readonly GL_DRAW_BUFFER7: number = 0x882C;
	public static readonly GL_DRAW_BUFFER8: number = 0x882D;
	public static readonly GL_DRAW_BUFFER9: number = 0x882E;
	public static readonly GL_DRAW_BUFFER10: number = 0x882F;
	public static readonly GL_DRAW_BUFFER11: number = 0x8830;
	public static readonly GL_DRAW_BUFFER12: number = 0x8831;
	public static readonly GL_DRAW_BUFFER13: number = 0x8832;
	public static readonly GL_DRAW_BUFFER14: number = 0x8833;
	public static readonly GL_DRAW_BUFFER15: number = 0x8834;
	public static readonly GL_MAX_FRAGMENT_UNIFORM_COMPONENTS: number = 0x8B49;
	public static readonly GL_MAX_VERTEX_UNIFORM_COMPONENTS: number = 0x8B4A;
	public static readonly GL_SAMPLER_3D: number = 0x8B5F;
	public static readonly GL_SAMPLER_2D_SHADOW: number = 0x8B62;
	public static readonly GL_FRAGMENT_SHADER_DERIVATIVE_HINT: number = 0x8B8B;
	public static readonly GL_PIXEL_PACK_BUFFER: number = 0x88EB;
	public static readonly GL_PIXEL_UNPACK_BUFFER: number = 0x88EC;
	public static readonly GL_PIXEL_PACK_BUFFER_BINDING: number = 0x88ED;
	public static readonly GL_PIXEL_UNPACK_BUFFER_BINDING: number = 0x88EF;
	public static readonly GL_FLOAT_MAT2x3: number = 0x8B65;
	public static readonly GL_FLOAT_MAT2x4: number = 0x8B66;
	public static readonly GL_FLOAT_MAT3x2: number = 0x8B67;
	public static readonly GL_FLOAT_MAT3x4: number = 0x8B68;
	public static readonly GL_FLOAT_MAT4x2: number = 0x8B69;
	public static readonly GL_FLOAT_MAT4x3: number = 0x8B6A;
	public static readonly GL_SRGB: number = 0x8C40;
	public static readonly GL_SRGB8: number = 0x8C41;
	public static readonly GL_SRGB8_ALPHA8: number = 0x8C43;
	public static readonly GL_COMPARE_REF_TO_TEXTURE: number = 0x884E;
	public static readonly GL_MAJOR_VERSION: number = 0x821B;
	public static readonly GL_MINOR_VERSION: number = 0x821C;
	public static readonly GL_NUM_EXTENSIONS: number = 0x821D;
	public static readonly GL_RGBA32F: number = 0x8814;
	public static readonly GL_RGB32F: number = 0x8815;
	public static readonly GL_RGBA16F: number = 0x881A;
	public static readonly GL_RGB16F: number = 0x881B;
	public static readonly GL_VERTEX_ATTRIB_ARRAY_INTEGER: number = 0x88FD;
	public static readonly GL_MAX_ARRAY_TEXTURE_LAYERS: number = 0x88FF;
	public static readonly GL_MIN_PROGRAM_TEXEL_OFFSET: number = 0x8904;
	public static readonly GL_MAX_PROGRAM_TEXEL_OFFSET: number = 0x8905;
	public static readonly GL_MAX_VARYING_COMPONENTS: number = 0x8B4B;
	public static readonly GL_TEXTURE_2D_ARRAY: number = 0x8C1A;
	public static readonly GL_TEXTURE_BINDING_2D_ARRAY: number = 0x8C1D;
	public static readonly GL_R11F_G11F_B10F: number = 0x8C3A;
	public static readonly GL_UNSIGNED_INT_10F_11F_11F_REV: number = 0x8C3B;
	public static readonly GL_RGB9_E5: number = 0x8C3D;
	public static readonly GL_UNSIGNED_INT_5_9_9_9_REV: number = 0x8C3E;
	public static readonly GL_TRANSFORM_FEEDBACK_VARYING_MAX_LENGTH: number = 0x8C76;
	public static readonly GL_TRANSFORM_FEEDBACK_BUFFER_MODE: number = 0x8C7F;
	public static readonly GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_COMPONENTS: number = 0x8C80;
	public static readonly GL_TRANSFORM_FEEDBACK_VARYINGS: number = 0x8C83;
	public static readonly GL_TRANSFORM_FEEDBACK_BUFFER_START: number = 0x8C84;
	public static readonly GL_TRANSFORM_FEEDBACK_BUFFER_SIZE: number = 0x8C85;
	public static readonly GL_TRANSFORM_FEEDBACK_PRIMITIVES_WRITTEN: number = 0x8C88;
	public static readonly GL_RASTERIZER_DISCARD: number = 0x8C89;
	public static readonly GL_MAX_TRANSFORM_FEEDBACK_INTERLEAVED_COMPONENTS: number = 0x8C8A;
	public static readonly GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_ATTRIBS: number = 0x8C8B;
	public static readonly GL_INTERLEAVED_ATTRIBS: number = 0x8C8C;
	public static readonly GL_SEPARATE_ATTRIBS: number = 0x8C8D;
	public static readonly GL_TRANSFORM_FEEDBACK_BUFFER: number = 0x8C8E;
	public static readonly GL_TRANSFORM_FEEDBACK_BUFFER_BINDING: number = 0x8C8F;
	public static readonly GL_RGBA32UI: number = 0x8D70;
	public static readonly GL_RGB32UI: number = 0x8D71;
	public static readonly GL_RGBA16UI: number = 0x8D76;
	public static readonly GL_RGB16UI: number = 0x8D77;
	public static readonly GL_RGBA8UI: number = 0x8D7C;
	public static readonly GL_RGB8UI: number = 0x8D7D;
	public static readonly GL_RGBA32I: number = 0x8D82;
	public static readonly GL_RGB32I: number = 0x8D83;
	public static readonly GL_RGBA16I: number = 0x8D88;
	public static readonly GL_RGB16I: number = 0x8D89;
	public static readonly GL_RGBA8I: number = 0x8D8E;
	public static readonly GL_RGB8I: number = 0x8D8F;
	public static readonly GL_RED_INTEGER: number = 0x8D94;
	public static readonly GL_RGB_INTEGER: number = 0x8D98;
	public static readonly GL_RGBA_INTEGER: number = 0x8D99;
	public static readonly GL_SAMPLER_2D_ARRAY: number = 0x8DC1;
	public static readonly GL_SAMPLER_2D_ARRAY_SHADOW: number = 0x8DC4;
	public static readonly GL_SAMPLER_CUBE_SHADOW: number = 0x8DC5;
	public static readonly GL_UNSIGNED_INT_VEC2: number = 0x8DC6;
	public static readonly GL_UNSIGNED_INT_VEC3: number = 0x8DC7;
	public static readonly GL_UNSIGNED_INT_VEC4: number = 0x8DC8;
	public static readonly GL_INT_SAMPLER_2D: number = 0x8DCA;
	public static readonly GL_INT_SAMPLER_3D: number = 0x8DCB;
	public static readonly GL_INT_SAMPLER_CUBE: number = 0x8DCC;
	public static readonly GL_INT_SAMPLER_2D_ARRAY: number = 0x8DCF;
	public static readonly GL_UNSIGNED_INT_SAMPLER_2D: number = 0x8DD2;
	public static readonly GL_UNSIGNED_INT_SAMPLER_3D: number = 0x8DD3;
	public static readonly GL_UNSIGNED_INT_SAMPLER_CUBE: number = 0x8DD4;
	public static readonly GL_UNSIGNED_INT_SAMPLER_2D_ARRAY: number = 0x8DD7;
	public static readonly GL_BUFFER_ACCESS_FLAGS: number = 0x911F;
	public static readonly GL_BUFFER_MAP_LENGTH: number = 0x9120;
	public static readonly GL_BUFFER_MAP_OFFSET: number = 0x9121;
	public static readonly GL_DEPTH_COMPONENT32F: number = 0x8CAC;
	public static readonly GL_DEPTH32F_STENCIL8: number = 0x8CAD;
	public static readonly GL_FLOAT_32_UNSIGNED_INT_24_8_REV: number = 0x8DAD;
	public static readonly GL_FRAMEBUFFER_ATTACHMENT_COLOR_ENCODING: number = 0x8210;
	public static readonly GL_FRAMEBUFFER_ATTACHMENT_COMPONENT_TYPE: number = 0x8211;
	public static readonly GL_FRAMEBUFFER_ATTACHMENT_RED_SIZE: number = 0x8212;
	public static readonly GL_FRAMEBUFFER_ATTACHMENT_GREEN_SIZE: number = 0x8213;
	public static readonly GL_FRAMEBUFFER_ATTACHMENT_BLUE_SIZE: number = 0x8214;
	public static readonly GL_FRAMEBUFFER_ATTACHMENT_ALPHA_SIZE: number = 0x8215;
	public static readonly GL_FRAMEBUFFER_ATTACHMENT_DEPTH_SIZE: number = 0x8216;
	public static readonly GL_FRAMEBUFFER_ATTACHMENT_STENCIL_SIZE: number = 0x8217;
	public static readonly GL_FRAMEBUFFER_DEFAULT: number = 0x8218;
	public static readonly GL_FRAMEBUFFER_UNDEFINED: number = 0x8219;
	public static readonly GL_DEPTH_STENCIL_ATTACHMENT: number = 0x821A;
	public static readonly GL_DEPTH_STENCIL: number = 0x84F9;
	public static readonly GL_UNSIGNED_INT_24_8: number = 0x84FA;
	public static readonly GL_DEPTH24_STENCIL8: number = 0x88F0;
	public static readonly GL_UNSIGNED_NORMALIZED: number = 0x8C17;
	public static readonly GL_DRAW_FRAMEBUFFER_BINDING: number = GL20.GL_FRAMEBUFFER_BINDING;
	public static readonly GL_READ_FRAMEBUFFER: number = 0x8CA8;
	public static readonly GL_DRAW_FRAMEBUFFER: number = 0x8CA9;
	public static readonly GL_READ_FRAMEBUFFER_BINDING: number = 0x8CAA;
	public static readonly GL_RENDERBUFFER_SAMPLES: number = 0x8CAB;
	public static readonly GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LAYER: number = 0x8CD4;
	public static readonly GL_MAX_COLOR_ATTACHMENTS: number = 0x8CDF;
	public static readonly GL_COLOR_ATTACHMENT1: number = 0x8CE1;
	public static readonly GL_COLOR_ATTACHMENT2: number = 0x8CE2;
	public static readonly GL_COLOR_ATTACHMENT3: number = 0x8CE3;
	public static readonly GL_COLOR_ATTACHMENT4: number = 0x8CE4;
	public static readonly GL_COLOR_ATTACHMENT5: number = 0x8CE5;
	public static readonly GL_COLOR_ATTACHMENT6: number = 0x8CE6;
	public static readonly GL_COLOR_ATTACHMENT7: number = 0x8CE7;
	public static readonly GL_COLOR_ATTACHMENT8: number = 0x8CE8;
	public static readonly GL_COLOR_ATTACHMENT9: number = 0x8CE9;
	public static readonly GL_COLOR_ATTACHMENT10: number = 0x8CEA;
	public static readonly GL_COLOR_ATTACHMENT11: number = 0x8CEB;
	public static readonly GL_COLOR_ATTACHMENT12: number = 0x8CEC;
	public static readonly GL_COLOR_ATTACHMENT13: number = 0x8CED;
	public static readonly GL_COLOR_ATTACHMENT14: number = 0x8CEE;
	public static readonly GL_COLOR_ATTACHMENT15: number = 0x8CEF;
	public static readonly GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE: number = 0x8D56;
	public static readonly GL_MAX_SAMPLES: number = 0x8D57;
	public static readonly GL_HALF_FLOAT: number = 0x140B;
	public static readonly GL_MAP_READ_BIT: number = 0x0001;
	public static readonly GL_MAP_WRITE_BIT: number = 0x0002;
	public static readonly GL_MAP_INVALIDATE_RANGE_BIT: number = 0x0004;
	public static readonly GL_MAP_INVALIDATE_BUFFER_BIT: number = 0x0008;
	public static readonly GL_MAP_FLUSH_EXPLICIT_BIT: number = 0x0010;
	public static readonly GL_MAP_UNSYNCHRONIZED_BIT: number = 0x0020;
	public static readonly GL_RG: number = 0x8227;
	public static readonly GL_RG_INTEGER: number = 0x8228;
	public static readonly GL_R8: number = 0x8229;
	public static readonly GL_RG8: number = 0x822B;
	public static readonly GL_R16F: number = 0x822D;
	public static readonly GL_R32F: number = 0x822E;
	public static readonly GL_RG16F: number = 0x822F;
	public static readonly GL_RG32F: number = 0x8230;
	public static readonly GL_R8I: number = 0x8231;

	public static readonly GL_R8UI: number = 0x8232;
	public static readonly GL_R16I: number = 0x8233;
	public static readonly GL_R16UI: number = 0x8234;
	public static readonly GL_R32I: number = 0x8235;
	public static readonly GL_R32UI: number = 0x8236;
	public static readonly GL_RG8I: number = 0x8237;
	public static readonly GL_RG8UI: number = 0x8238;
	public static readonly GL_RG16I: number = 0x8239;
	public static readonly GL_RG16UI: number = 0x823A;
	public static readonly GL_RG32I: number = 0x823B;
	public static readonly GL_RG32UI: number = 0x823C;
	public static readonly GL_VERTEX_ARRAY_BINDING: number = 0x85B5;
	public static readonly GL_R8_SNORM: number = 0x8F94;
	public static readonly GL_RG8_SNORM: number = 0x8F95;
	public static readonly GL_RGB8_SNORM: number = 0x8F96;
	public static readonly GL_RGBA8_SNORM: number = 0x8F97;
	public static readonly GL_SIGNED_NORMALIZED: number = 0x8F9C;
	public static readonly GL_PRIMITIVE_RESTART_FIXED_INDEX: number = 0x8D69;
	public static readonly GL_COPY_READ_BUFFER: number = 0x8F36;
	public static readonly GL_COPY_WRITE_BUFFER: number = 0x8F37;
	public static readonly GL_COPY_READ_BUFFER_BINDING: number = 0x8F36;
	public static readonly GL_COPY_WRITE_BUFFER_BINDING: number = 0x8F37;
	public static readonly GL_UNIFORM_BUFFER: number = 0x8A11;
	public static readonly GL_UNIFORM_BUFFER_BINDING: number = 0x8A28;
	public static readonly GL_UNIFORM_BUFFER_START: number = 0x8A29;
	public static readonly GL_UNIFORM_BUFFER_SIZE: number = 0x8A2A;
	public static readonly GL_MAX_VERTEX_UNIFORM_BLOCKS: number = 0x8A2B;
	public static readonly GL_MAX_FRAGMENT_UNIFORM_BLOCKS: number = 0x8A2D;
	public static readonly GL_MAX_COMBINED_UNIFORM_BLOCKS: number = 0x8A2E;
	public static readonly GL_MAX_UNIFORM_BUFFER_BINDINGS: number = 0x8A2F;
	public static readonly GL_MAX_UNIFORM_BLOCK_SIZE: number = 0x8A30;
	public static readonly GL_MAX_COMBINED_VERTEX_UNIFORM_COMPONENTS: number = 0x8A31;
	public static readonly GL_MAX_COMBINED_FRAGMENT_UNIFORM_COMPONENTS: number = 0x8A33;
	public static readonly GL_UNIFORM_BUFFER_OFFSET_ALIGNMENT: number = 0x8A34;
	public static readonly GL_ACTIVE_UNIFORM_BLOCK_MAX_NAME_LENGTH: number = 0x8A35;
	public static readonly GL_ACTIVE_UNIFORM_BLOCKS: number = 0x8A36;
	public static readonly GL_UNIFORM_TYPE: number = 0x8A37;
	public static readonly GL_UNIFORM_SIZE: number = 0x8A38;
	public static readonly GL_UNIFORM_NAME_LENGTH: number = 0x8A39;
	public static readonly GL_UNIFORM_BLOCK_INDEX: number = 0x8A3A;
	public static readonly GL_UNIFORM_OFFSET: number = 0x8A3B;
	public static readonly GL_UNIFORM_ARRAY_STRIDE: number = 0x8A3C;
	public static readonly GL_UNIFORM_MATRIX_STRIDE: number = 0x8A3D;
	public static readonly GL_UNIFORM_IS_ROW_MAJOR: number = 0x8A3E;
	public static readonly GL_UNIFORM_BLOCK_BINDING: number = 0x8A3F;
	public static readonly GL_UNIFORM_BLOCK_DATA_SIZE: number = 0x8A40;
	public static readonly GL_UNIFORM_BLOCK_NAME_LENGTH: number = 0x8A41;
	public static readonly GL_UNIFORM_BLOCK_ACTIVE_UNIFORMS: number = 0x8A42;
	public static readonly GL_UNIFORM_BLOCK_ACTIVE_UNIFORM_INDICES: number = 0x8A43;
	public static readonly GL_UNIFORM_BLOCK_REFERENCED_BY_VERTEX_SHADER: number = 0x8A44;
	public static readonly GL_UNIFORM_BLOCK_REFERENCED_BY_FRAGMENT_SHADER: number = 0x8A46;
	// GL_INVALID_INDEX is defined as 0xFFFFFFFFu in C.
	public static readonly GL_INVALID_INDEX: number = -1;
	public static readonly GL_MAX_VERTEX_OUTPUT_COMPONENTS: number = 0x9122;
	public static readonly GL_MAX_FRAGMENT_INPUT_COMPONENTS: number = 0x9125;
	public static readonly GL_MAX_SERVER_WAIT_TIMEOUT: number = 0x9111;
	public static readonly GL_OBJECT_TYPE: number = 0x9112;
	public static readonly GL_SYNC_CONDITION: number = 0x9113;
	public static readonly GL_SYNC_STATUS: number = 0x9114;
	public static readonly GL_SYNC_FLAGS: number = 0x9115;
	public static readonly GL_SYNC_FENCE: number = 0x9116;
	public static readonly GL_SYNC_GPU_COMMANDS_COMPLETE: number = 0x9117;
	public static readonly GL_UNSIGNALED: number = 0x9118;
	public static readonly GL_SIGNALED: number = 0x9119;
	public static readonly GL_ALREADY_SIGNALED: number = 0x911A;
	public static readonly GL_TIMEOUT_EXPIRED: number = 0x911B;
	public static readonly GL_CONDITION_SATISFIED: number = 0x911C;
	public static readonly GL_WAIT_FAILED: number = 0x911D;
	public static readonly GL_SYNC_FLUSH_COMMANDS_BIT: number = 0x00000001;
	// GL_TIMEOUT_IGNORED is defined as 0xFFFFFFFFFFFFFFFFull in C.
	public static readonly GL_TIMEOUT_IGNORED: number = -1;
	public static readonly GL_VERTEX_ATTRIB_ARRAY_DIVISOR: number = 0x88FE;
	public static readonly GL_ANY_SAMPLES_PASSED: number = 0x8C2F;
	public static readonly GL_ANY_SAMPLES_PASSED_CONSERVATIVE: number = 0x8D6A;
	public static readonly GL_SAMPLER_BINDING: number = 0x8919;
	public static readonly GL_RGB10_A2UI: number = 0x906F;
	public static readonly GL_TEXTURE_SWIZZLE_R: number = 0x8E42;
	public static readonly GL_TEXTURE_SWIZZLE_G: number = 0x8E43;
	public static readonly GL_TEXTURE_SWIZZLE_B: number = 0x8E44;
	public static readonly GL_TEXTURE_SWIZZLE_A: number = 0x8E45;
	public static readonly GL_GREEN: number = 0x1905;
	public static readonly GL_BLUE: number = 0x1905;
	public static readonly GL_INT_2_10_10_10_REV: number = 0x8D9F;
	public static readonly GL_TRANSFORM_FEEDBACK: number = 0x8E22;
	public static readonly GL_TRANSFORM_FEEDBACK_PAUSED: number = 0x8E23;
	public static readonly GL_TRANSFORM_FEEDBACK_ACTIVE: number = 0x8E24;
	public static readonly GL_TRANSFORM_FEEDBACK_BINDING: number = 0x8E25;
	public static readonly GL_PROGRAM_BINARY_RETRIEVABLE_HINT: number = 0x8257;
	public static readonly GL_PROGRAM_BINARY_LENGTH: number = 0x8741;
	public static readonly GL_NUM_PROGRAM_BINARY_FORMATS: number = 0x87FE;
	public static readonly GL_PROGRAM_BINARY_FORMATS: number = 0x87FF;
	public static readonly GL_COMPRESSED_R11_EAC: number = 0x9270;
	public static readonly GL_COMPRESSED_SIGNED_R11_EAC: number = 0x9271;
	public static readonly GL_COMPRESSED_RG11_EAC: number = 0x9272;
	public static readonly GL_COMPRESSED_SIGNED_RG11_EAC: number = 0x9273;
	public static readonly GL_COMPRESSED_RGB8_ETC2: number = 0x9274;
	public static readonly GL_COMPRESSED_SRGB8_ETC2: number = 0x9275;
	public static readonly GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2: number = 0x9276;
	public static readonly GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2: number = 0x9277;
	public static readonly GL_COMPRESSED_RGBA8_ETC2_EAC: number = 0x9278;
	public static readonly GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC: number = 0x9279;
	public static readonly GL_TEXTURE_IMMUTABLE_FORMAT: number = 0x912F;
	public static readonly GL_MAX_ELEMENT_INDEX: number = 0x8D6B;
	public static readonly GL_NUM_SAMPLE_COUNTS: number = 0x9380;
	public static readonly GL_TEXTURE_IMMUTABLE_LEVELS: number = 0x82DF;

  // C function void glReadBuffer ( GLenum mode )
  public glReadBuffer(mode: number): void { Gdx.gl30.glReadBuffer(mode) }

	// C function void glDrawRangeElements ( GLenum mode, GLuint start, GLuint end, GLsizei count, GLenum type, const GLvoid *indices )
	// C function void glDrawRangeElements ( GLenum mode, GLuint start, GLuint end, GLsizei count, GLenum type, GLsizei offset )
	public glDrawRangeElements(mode: number, start: number, end: number, count: number, type: number, indices: Buffer | number): void { Gdx.gl30.glDrawRangeElements(mode, start, end, count, type, indices as any) }
	public glTexImage2D (target: number, level: number, internalformat: number, width: number, height: number, border: number, format: number, type: number, offset: number): void { Gdx.gl30.glTexImage2D(target, level, internalformat, width, height, border, format, type, offset) }
	public glTexImage3D (target: number, level: number, internalformat: number, width: number, height: number, depth: number, border: number, format: number, type: number, pixels: Buffer): void;
	public glTexImage3D (target: number, level: number, internalformat: number, width: number, height: number, depth: number, border: number, format: number, type: number, offset: number): void;
	public glTexImage3D (...args: any[]): void { Gdx.gl30.glTexImage3D(args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8], args[9]) }
	public glTexSubImage2D (target: number, level: number, xoffset: number, yoffset: number, width: number, height: number, format: number, type: number, offset: number): void { Gdx.gl30.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, offset) }
	public glTexSubImage3D (target: number, level: number, xoffset: number, yoffset: number, zoffset: number, width: number, height: number, depth: number, format: number, type: number, pixels: Buffer): void
	public glTexSubImage3D (target: number, level: number, xoffset: number, yoffset: number, zoffset: number, width: number, height: number, depth: number, format: number, type: number, offset: number): void
  public glTexSubImage3D (...args: any[]): void { Gdx.gl30.glTexSubImage3D(args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8], args[9], args[10]) }
	public glCopyTexSubImage3D (target: number, level: number, xoffset: number, yoffset: number, zoffset: number, x: number, y: number, width: number, height: number): void { Gdx.gl30.glCopyTexSubImage3D(target, level, xoffset, yoffset, zoffset, x, y, width, height) }

  public glGenQueries (n: number, ids: number[], offset: number): void
  public glGenQueries (n: number, ids: IntBuffer): void
  public glGenQueries (...args: any[]): void {
    if (args.length === 2) {
      Gdx.gl30.glGenQueries(args[0], args[1]);
      return;
    }

    if (args.length === 3) {
      Gdx.gl30.glGenQueries(args[0], args[1], args[2]);
      return;
    }

    throw new Error("Invalid number of arguments");
  }

  public glDeleteQueries (n: number, ids: number[], offset: number): void
  public glDeleteQueries (n: number, ids: IntBuffer): void
  public glDeleteQueries (...args: any[]): void {
    if (args.length === 2) {
      Gdx.gl30.glDeleteQueries(args[0], args[1]);
      return;
    }

    if (args.length === 3) {
      Gdx.gl30.glDeleteQueries(args[0], args[1], args[2]);
      return;
    }

    throw new Error("Invalid number of arguments");
  }

  public glIsQuery (id: number): boolean { return Gdx.gl30.glIsQuery(id) }
  public glBeginQuery(target: number, id: number): void { Gdx.gl30.glBeginQuery(target, id) }
  public glEndQuery(target: number): void { Gdx.gl30.glEndQuery(target) }

  public glGetQueryiv(target: number, pname: number, params: IntBuffer): void { Gdx.gl30.glGetQueryiv(target, pname, params as any) }
  public glGetQueryObjectuiv(id: number, pname: number, params: IntBuffer): void { Gdx.gl30.glGetQueryObjectuiv(id, pname, params as any) }

  public glUnmapBuffer(target: number): boolean { return Gdx.gl30.glUnmapBuffer(target) }
  public glGetBufferPointerv(target: number, pname: number): Buffer { return Gdx.gl30.glGetBufferPointerv(target, pname) }
  public glDrawBuffers(n: number, bufs: IntBuffer): void { Gdx.gl30.glDrawBuffers(n, bufs) }

  public glUniformMatrix2x3fv(location: number, count: number, transpose: boolean, value: Float32Array): void { Gdx.gl30.glUniformMatrix2x3fv(location, count, transpose, value as any) }
  public glUniformMatrix3x2fv(location: number, count: number, transpose: boolean, value: Float32Array): void { Gdx.gl30.glUniformMatrix3x2fv(location, count, transpose, value as any) }
  public glUniformMatrix2x4fv(location: number, count: number, transpose: boolean, value: Float32Array): void { Gdx.gl30.glUniformMatrix2x4fv(location, count, transpose, value as any) }
  public glUniformMatrix4x2fv(location: number, count: number, transpose: boolean, value: Float32Array): void { Gdx.gl30.glUniformMatrix4x2fv(location, count, transpose, value as any) }
  public glUniformMatrix3x4fv(location: number, count: number, transpose: boolean, value: Float32Array): void { Gdx.gl30.glUniformMatrix3x4fv(location, count, transpose, value as any) }
  public glUniformMatrix4x3fv(location: number, count: number, transpose: boolean, value: Float32Array): void { Gdx.gl30.glUniformMatrix4x3fv(location, count, transpose, value as any) }
  public glBlitFramebuffer(srcX0: number, srcY0: number, srcX1: number, srcY1: number, dstX0: number, dstY0: number, dstX1: number, dstY1: number, mask: number, filter: number): void { Gdx.gl30.glBlitFramebuffer(srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter) }
  public glRenderbufferStorageMultisample(target: number, samples: number, internalformat: number, width: number, height: number): void { Gdx.gl30.glRenderbufferStorageMultisample(target, samples, internalformat, width, height) }
  public glFramebufferTextureLayer(target: number, attachment: number, texture: number, level: number, layer: number): void { Gdx.gl30.glFramebufferTextureLayer(target, attachment, texture, level, layer) }
  public glMapBufferRange(target: number, offset: number, length: number, access: number): Buffer { return Gdx.gl30.glMapBufferRange(target, offset, length, access) }
  public glFlushMappedBufferRange(target: number, offset: number, length: number): void { Gdx.gl30.glFlushMappedBufferRange(target, offset, length) }
  public glBindVertexArray(array: number): void { Gdx.gl30.glBindVertexArray(array) }
  public glDeleteVertexArrays(n: number, arrays: number[], offset: number): void
  public glDeleteVertexArrays(n: number, arrays: IntBuffer): void
  public glDeleteVertexArrays(...args: any[]): void {
    if (args.length === 2) {
      Gdx.gl30.glDeleteVertexArrays(args[0], args[1]);
      return;
    }
    if (args.length === 3) {
      Gdx.gl30.glDeleteVertexArrays(args[0], args[1], args[2]);
      return;
    }
    throw new Error("Invalid number of arguments");
  }
  public glGenVertexArrays(n: number, arrays: number[], offset: number): void
  public glGenVertexArrays(n: number, arrays: IntBuffer): void
  public glGenVertexArrays(...args: any[]): void {
    if (args.length === 2) {
      Gdx.gl30.glGenVertexArrays(args[0], args[1]);
      return;
    }
    if (args.length === 3) {
      Gdx.gl30.glGenVertexArrays(args[0], args[1], args[2]);
      return;
    }
    throw new Error("Invalid number of arguments");
  }
  public glIsVertexArray(array: number): boolean { return Gdx.gl30.glIsVertexArray(array) }
  public glBeginTransformFeedback(primitiveMode: number): void { Gdx.gl30.glBeginTransformFeedback(primitiveMode) }
  public glEndTransformFeedback(): void { Gdx.gl30.glEndTransformFeedback() }
  public glBindBufferRange(target: number, index: number, buffer: number, offset: number, size: number): void { Gdx.gl30.glBindBufferRange(target, index, buffer, offset, size) }
  public glBindBufferBase(target: number, index: number, buffer: number): void { Gdx.gl30.glBindBufferBase(target, index, buffer) }
  public glTransformFeedbackVaryings(program: number, varyings: string[], bufferMode: number): void { Gdx.gl30.glTransformFeedbackVaryings(program, varyings as any, bufferMode) }
  public glVertexAttribIPointer(index: number, size: number, type: number, stride: number, offset: number): void { Gdx.gl30.glVertexAttribIPointer(index, size, type, stride, offset) }
  public glGetVertexAttribIiv(index: number, pname: number, params: Int32Array): void { Gdx.gl30.glGetVertexAttribIiv(index, pname, params as any) }
  public glGetVertexAttribIuiv(index: number, pname: number, params: Uint32Array): void { Gdx.gl30.glGetVertexAttribIuiv(index, pname, params as any) }
  public glVertexAttribI4i(index: number, x: number, y: number, z: number, w: number): void { Gdx.gl30.glVertexAttribI4i(index, x, y, z, w) }
  public glVertexAttribI4ui(index: number, x: number, y: number, z: number, w: number): void { Gdx.gl30.glVertexAttribI4ui(index, x, y, z, w) }
  public glGetUniformuiv(program: number, location: number, params: Uint32Array): void { Gdx.gl30.glGetUniformuiv(program, location, params as any) }
  public glGetFragDataLocation(program: number, name: string): number { return Gdx.gl30.glGetFragDataLocation(program, name) }
  public glUniform1uiv(location: number, count: number, value: Uint32Array): void { Gdx.gl30.glUniform1uiv(location, count, value as any) }
  public glUniform3uiv(location: number, count: number, value: Uint32Array): void { Gdx.gl30.glUniform3uiv(location, count, value as any) }
  public glUniform4uiv(location: number, count: number, value: Uint32Array): void { Gdx.gl30.glUniform4uiv(location, count, value as any) }
  public glClearBufferiv(buffer: number, drawbuffer: number, value: Int32Array): void { Gdx.gl30.glClearBufferiv(buffer, drawbuffer, value as any) }
  public glClearBufferuiv(buffer: number, drawbuffer: number, value: Uint32Array): void { Gdx.gl30.glClearBufferuiv(buffer, drawbuffer, value as any) }
  public glClearBufferfv(buffer: number, drawbuffer: number, value: Float32Array): void { Gdx.gl30.glClearBufferfv(buffer, drawbuffer, value as any) }
  public glClearBufferfi(buffer: number, drawbuffer: number, depth: number, stencil: number): void { Gdx.gl30.glClearBufferfi(buffer, drawbuffer, depth, stencil) }
  public glGetStringi(name: number, index: number): string { return Gdx.gl30.glGetStringi(name, index) }
  public glCopyBufferSubData(readTarget: number, writeTarget: number, readOffset: number, writeOffset: number, size: number): void { Gdx.gl30.glCopyBufferSubData(readTarget, writeTarget, readOffset, writeOffset, size) }
  public glGetUniformIndices(program: number, uniformNames: string[], uniformIndices: Int32Array): void { Gdx.gl30.glGetUniformIndices(program, uniformNames as any, uniformIndices as any) }
  public glGetActiveUniformsiv(program: number, uniformCount: number, uniformIndices: Int32Array, pname: number, params: Int32Array): void { Gdx.gl30.glGetActiveUniformsiv(program, uniformCount, uniformIndices as any, pname, params as any) }
  public glGetUniformBlockIndex(program: number, uniformBlockName: string): number { return Gdx.gl30.glGetUniformBlockIndex(program, uniformBlockName) }
  public glGetActiveUniformBlockiv(program: number, uniformBlockIndex: number, pname: number, params: Int32Array): void { Gdx.gl30.glGetActiveUniformBlockiv(program, uniformBlockIndex, pname, params as any) }
  public glGetActiveUniformBlockName(program: number, uniformBlockIndex: number, length: Buffer, uniformBlockName: Buffer): void { Gdx.gl30.glGetActiveUniformBlockName(program, uniformBlockIndex, length, uniformBlockName) }
  public glUniformBlockBinding(program: number, uniformBlockIndex: number, uniformBlockBinding: number): void { Gdx.gl30.glUniformBlockBinding(program, uniformBlockIndex, uniformBlockBinding) }
  public glDrawArraysInstanced(mode: number, first: number, count: number, instanceCount: number): void { Gdx.gl30.glDrawArraysInstanced(mode, first, count, instanceCount) }
  public glDrawElementsInstanced(mode: number, count: number, type: number, indicesOffset: number, instanceCount: number): void { Gdx.gl30.glDrawElementsInstanced(mode, count, type, indicesOffset, instanceCount) }
  public glGetInteger64v(pname: number, params: number[]): void { Gdx.gl30.glGetInteger64v(pname, params as any) }
  public glGetBufferParameteri64v(target: number, pname: number, params: number[]): void { Gdx.gl30.glGetBufferParameteri64v(target, pname, params as any) }
}
