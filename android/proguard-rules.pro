# To enable ProGuard in your project, edit project.properties
# to define the proguard.config property as described in that file.
#
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in ${sdk.dir}/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
#
# For more details, see
#   https://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-verbose

-dontwarn android.support.**
-dontwarn com.badlogic.gdx.backends.android.AndroidFragmentApplication
-dontwarn org.jspecify.annotations.NullMarked
-dontshrink

-dontwarn com.aayushatharva.brotli4j.Brotli4jLoader
-dontwarn com.aayushatharva.brotli4j.decoder.DecoderJNI$Status
-dontwarn com.aayushatharva.brotli4j.decoder.DecoderJNI$Wrapper
-dontwarn com.aayushatharva.brotli4j.encoder.BrotliEncoderChannel
-dontwarn com.aayushatharva.brotli4j.encoder.Encoder$Mode
-dontwarn com.aayushatharva.brotli4j.encoder.Encoder$Parameters
-dontwarn com.apple.eio.FileManager
-dontwarn com.badlogic.gdx.maps.tiled.BaseTmxMapLoader$Parameters
-dontwarn com.fasterxml.jackson.databind.DeserializationContext
-dontwarn com.fasterxml.jackson.databind.JsonDeserializer
-dontwarn com.fasterxml.jackson.databind.JsonSerializer
-dontwarn com.fasterxml.jackson.databind.Module
-dontwarn com.fasterxml.jackson.databind.ObjectMapper
-dontwarn com.fasterxml.jackson.databind.SerializationFeature
-dontwarn com.fasterxml.jackson.databind.SerializerProvider
-dontwarn com.fasterxml.jackson.databind.exc.InvalidFormatException
-dontwarn com.fasterxml.jackson.databind.module.SimpleModule
-dontwarn com.github.luben.zstd.Zstd
-dontwarn com.google.protobuf.ExtensionRegistry
-dontwarn com.google.protobuf.ExtensionRegistryLite
-dontwarn com.google.protobuf.MessageLite$Builder
-dontwarn com.google.protobuf.MessageLite
-dontwarn com.google.protobuf.MessageLiteOrBuilder
-dontwarn com.google.protobuf.Parser
-dontwarn com.google.protobuf.nano.CodedOutputByteBufferNano
-dontwarn com.google.protobuf.nano.MessageNano
-dontwarn com.jcraft.jzlib.Deflater
-dontwarn com.jcraft.jzlib.Inflater
-dontwarn com.jcraft.jzlib.JZlib$WrapperType
-dontwarn com.jcraft.jzlib.JZlib
-dontwarn com.ning.compress.BufferRecycler
-dontwarn com.ning.compress.lzf.ChunkDecoder
-dontwarn com.ning.compress.lzf.ChunkEncoder
-dontwarn com.ning.compress.lzf.LZFChunk
-dontwarn com.ning.compress.lzf.LZFEncoder
-dontwarn com.ning.compress.lzf.util.ChunkDecoderFactory
-dontwarn com.ning.compress.lzf.util.ChunkEncoderFactory
-dontwarn com.oracle.svm.core.annotate.Alias
-dontwarn com.oracle.svm.core.annotate.InjectAccessors
-dontwarn com.oracle.svm.core.annotate.RecomputeFieldValue$Kind
-dontwarn com.oracle.svm.core.annotate.RecomputeFieldValue
-dontwarn com.oracle.svm.core.annotate.TargetClass
-dontwarn com.sun.jna.FunctionMapper
-dontwarn com.sun.jna.JNIEnv
-dontwarn com.sun.jna.LastErrorException
-dontwarn com.sun.jna.Library
-dontwarn com.sun.jna.Native
-dontwarn com.sun.jna.NativeLibrary
-dontwarn com.sun.jna.Platform
-dontwarn edu.umd.cs.findbugs.annotations.SuppressFBWarnings
-dontwarn io.netty.channel.epoll.Epoll
-dontwarn io.netty.channel.epoll.EpollChannelOption
-dontwarn io.netty.channel.epoll.EpollDatagramChannel
-dontwarn io.netty.channel.epoll.EpollDomainSocketChannel
-dontwarn io.netty.channel.epoll.EpollEventLoopGroup
-dontwarn io.netty.channel.epoll.EpollServerDomainSocketChannel
-dontwarn io.netty.channel.epoll.EpollServerSocketChannel
-dontwarn io.netty.channel.epoll.EpollSocketChannel
-dontwarn io.netty.channel.kqueue.KQueue
-dontwarn io.netty.channel.kqueue.KQueueChannelOption
-dontwarn io.netty.channel.kqueue.KQueueDatagramChannel
-dontwarn io.netty.channel.kqueue.KQueueDomainSocketChannel
-dontwarn io.netty.channel.kqueue.KQueueEventLoopGroup
-dontwarn io.netty.channel.kqueue.KQueueServerDomainSocketChannel
-dontwarn io.netty.channel.kqueue.KQueueServerSocketChannel
-dontwarn io.netty.channel.kqueue.KQueueSocketChannel
-dontwarn io.netty.handler.codec.haproxy.HAProxyMessage
-dontwarn io.netty.handler.codec.haproxy.HAProxyMessageDecoder
-dontwarn io.netty.handler.codec.haproxy.HAProxyProxiedProtocol$TransportProtocol
-dontwarn io.netty.handler.codec.haproxy.HAProxyProxiedProtocol
-dontwarn io.netty.internal.tcnative.AsyncSSLPrivateKeyMethod
-dontwarn io.netty.internal.tcnative.AsyncTask
-dontwarn io.netty.internal.tcnative.Buffer
-dontwarn io.netty.internal.tcnative.CertificateCallback
-dontwarn io.netty.internal.tcnative.CertificateCompressionAlgo
-dontwarn io.netty.internal.tcnative.CertificateVerifier
-dontwarn io.netty.internal.tcnative.Library
-dontwarn io.netty.internal.tcnative.ResultCallback
-dontwarn io.netty.internal.tcnative.SSL
-dontwarn io.netty.internal.tcnative.SSLContext
-dontwarn io.netty.internal.tcnative.SSLPrivateKeyMethod
-dontwarn io.netty.internal.tcnative.SSLSession
-dontwarn io.netty.internal.tcnative.SSLSessionCache
-dontwarn io.netty.internal.tcnative.SessionTicketKey
-dontwarn io.netty.internal.tcnative.SniHostNameMatcher
-dontwarn io.vertx.codegen.annotations.CacheReturn
-dontwarn io.vertx.codegen.annotations.DataObject
-dontwarn io.vertx.codegen.annotations.Fluent
-dontwarn io.vertx.codegen.annotations.GenIgnore
-dontwarn io.vertx.codegen.annotations.ModuleGen
-dontwarn io.vertx.codegen.annotations.VertxGen
-dontwarn java.beans.BeanInfo
-dontwarn java.beans.IntrospectionException
-dontwarn java.beans.Introspector
-dontwarn java.beans.PropertyDescriptor
-dontwarn java.lang.instrument.ClassDefinition
-dontwarn java.lang.instrument.IllegalClassFormatException
-dontwarn java.lang.instrument.UnmodifiableClassException
-dontwarn javax.annotation.Nonnull
-dontwarn javax.annotation.meta.TypeQualifierDefault
-dontwarn javax.annotation.meta.TypeQualifierNickname
-dontwarn javax.annotation.meta.When
-dontwarn javax.lang.model.element.Modifier
-dontwarn javax.lang.model.element.NestingKind
-dontwarn javax.naming.NamingException
-dontwarn javax.naming.directory.DirContext
-dontwarn javax.naming.directory.InitialDirContext
-dontwarn javax.naming.ldap.LdapName
-dontwarn javax.naming.ldap.Rdn
-dontwarn javax.tools.Diagnostic
-dontwarn javax.tools.DiagnosticCollector
-dontwarn javax.tools.DiagnosticListener
-dontwarn javax.tools.FileObject
-dontwarn javax.tools.ForwardingJavaFileManager
-dontwarn javax.tools.JavaCompiler$CompilationTask
-dontwarn javax.tools.JavaCompiler
-dontwarn javax.tools.JavaFileManager$Location
-dontwarn javax.tools.JavaFileManager
-dontwarn javax.tools.JavaFileObject$Kind
-dontwarn javax.tools.JavaFileObject
-dontwarn javax.tools.SimpleJavaFileObject
-dontwarn javax.tools.StandardJavaFileManager
-dontwarn javax.tools.StandardLocation
-dontwarn javax.tools.ToolProvider
-dontwarn lzma.sdk.ICodeProgress
-dontwarn lzma.sdk.lzma.Encoder
-dontwarn net.jpountz.lz4.LZ4Compressor
-dontwarn net.jpountz.lz4.LZ4Exception
-dontwarn net.jpountz.lz4.LZ4Factory
-dontwarn net.jpountz.lz4.LZ4FastDecompressor
-dontwarn net.jpountz.xxhash.XXHash32
-dontwarn net.jpountz.xxhash.XXHashFactory
-dontwarn org.apache.log4j.Level
-dontwarn org.apache.log4j.Logger
-dontwarn org.apache.log4j.Priority
-dontwarn org.apache.logging.log4j.Level
-dontwarn org.apache.logging.log4j.LogManager
-dontwarn org.apache.logging.log4j.Logger
-dontwarn org.apache.logging.log4j.Marker
-dontwarn org.apache.logging.log4j.message.FormattedMessage
-dontwarn org.apache.logging.log4j.message.Message
-dontwarn org.apache.logging.log4j.message.MessageFactory
-dontwarn org.apache.logging.log4j.spi.ExtendedLogger
-dontwarn org.apache.logging.log4j.spi.ExtendedLoggerWrapper
-dontwarn org.bouncycastle.asn1.pkcs.PrivateKeyInfo
-dontwarn org.bouncycastle.asn1.x500.X500Name
-dontwarn org.bouncycastle.cert.X509CertificateHolder
-dontwarn org.bouncycastle.cert.X509v3CertificateBuilder
-dontwarn org.bouncycastle.cert.jcajce.JcaX509CertificateConverter
-dontwarn org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder
-dontwarn org.bouncycastle.jce.provider.BouncyCastleProvider
-dontwarn org.bouncycastle.openssl.PEMDecryptorProvider
-dontwarn org.bouncycastle.openssl.PEMEncryptedKeyPair
-dontwarn org.bouncycastle.openssl.PEMKeyPair
-dontwarn org.bouncycastle.openssl.PEMParser
-dontwarn org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter
-dontwarn org.bouncycastle.openssl.jcajce.JceOpenSSLPKCS8DecryptorProviderBuilder
-dontwarn org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder
-dontwarn org.bouncycastle.operator.ContentSigner
-dontwarn org.bouncycastle.operator.InputDecryptorProvider
-dontwarn org.bouncycastle.operator.OperatorCreationException
-dontwarn org.bouncycastle.operator.jcajce.JcaContentSignerBuilder
-dontwarn org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo
-dontwarn org.bouncycastle.pkcs.PKCSException
-dontwarn org.conscrypt.AllocatedBuffer
-dontwarn org.conscrypt.BufferAllocator
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.HandshakeListener
-dontwarn org.eclipse.jetty.alpn.ALPN$ClientProvider
-dontwarn org.eclipse.jetty.alpn.ALPN$Provider
-dontwarn org.eclipse.jetty.alpn.ALPN$ServerProvider
-dontwarn org.eclipse.jetty.alpn.ALPN
-dontwarn org.eclipse.jetty.npn.NextProtoNego$ClientProvider
-dontwarn org.eclipse.jetty.npn.NextProtoNego$Provider
-dontwarn org.eclipse.jetty.npn.NextProtoNego$ServerProvider
-dontwarn org.eclipse.jetty.npn.NextProtoNego
-dontwarn org.jboss.marshalling.ByteInput
-dontwarn org.jboss.marshalling.ByteOutput
-dontwarn org.jboss.marshalling.Marshaller
-dontwarn org.jboss.marshalling.MarshallerFactory
-dontwarn org.jboss.marshalling.MarshallingConfiguration
-dontwarn org.jboss.marshalling.Unmarshaller
-dontwarn org.slf4j.ILoggerFactory
-dontwarn org.slf4j.Logger
-dontwarn org.slf4j.LoggerFactory
-dontwarn org.slf4j.Marker
-dontwarn org.slf4j.helpers.FormattingTuple
-dontwarn org.slf4j.helpers.MessageFormatter
-dontwarn org.slf4j.helpers.NOPLoggerFactory
-dontwarn org.slf4j.spi.LocationAwareLogger
-dontwarn reactor.blockhound.BlockHound$Builder
-dontwarn reactor.blockhound.integration.BlockHoundIntegration
-dontwarn sun.nio.ch.DirectBuffer
-dontwarn sun.security.x509.AlgorithmId
-dontwarn sun.security.x509.CertificateAlgorithmId
-dontwarn sun.security.x509.CertificateSerialNumber
-dontwarn sun.security.x509.CertificateSubjectName
-dontwarn sun.security.x509.CertificateValidity
-dontwarn sun.security.x509.CertificateVersion
-dontwarn sun.security.x509.CertificateX509Key
-dontwarn sun.security.x509.X500Name
-dontwarn sun.security.x509.X509CertImpl
-dontwarn sun.security.x509.X509CertInfo

# Needed by the gdx-controllers official extension.
-keep class com.badlogic.gdx.controllers.android.AndroidControllers

# Needed by the Box2D official extension.
-keepclassmembers class com.badlogic.gdx.physics.box2d.World {
   boolean contactFilter(long, long);
   void    beginContact(long);
   void    endContact(long);
   void    preSolve(long, long);
   void    postSolve(long, long);
   boolean reportFixture(long);
   float   reportRayFixture(long, float, float, float, float, float);
}

# You will need the next three lines if you use scene2d for UI or gameplay.
# If you don't use scene2d at all, you can remove or comment out the next line:
-keep public class com.badlogic.gdx.scenes.scene2d.** { *; }
# You will need the next two lines if you use BitmapFont or any scene2d.ui text:
-keep public class com.badlogic.gdx.graphics.g2d.BitmapFont { *; }
# You will probably need this line in most cases:
-keep public class com.badlogic.gdx.graphics.Color { *; }

# These two lines are used with mapping files; see https://developer.android.com/build/shrink-code#retracing
-keepattributes LineNumberTable,SourceFile
-renamesourcefileattribute SourceFile
