package au.com.deanpike.uitestshared.util

import java.net.InetAddress
import okhttp3.tls.HandshakeCertificates
import okhttp3.tls.HeldCertificate

object ServerPort {
    var host: String = ""
    var port: Int = 8080

    val certificate: HeldCertificate by lazy {
        val localhost = InetAddress.getByName("localhost").getCanonicalHostName()
        val localhostCertificate: HeldCertificate = HeldCertificate.Builder()
            .addSubjectAlternativeName(localhost)
            .build()
        localhostCertificate
    }

    val serverCertificates: HandshakeCertificates by lazy {
        val serverCertificates: HandshakeCertificates = HandshakeCertificates.Builder()
            .heldCertificate(certificate)
            .build()
        serverCertificates
    }

    val clientCertificates: HandshakeCertificates by lazy {
        val clientCertificates: HandshakeCertificates = HandshakeCertificates.Builder()
            .addTrustedCertificate(certificate.certificate)
            .build()
        clientCertificates
    }
}