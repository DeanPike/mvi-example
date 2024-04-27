package au.com.deanpike.uitestshared.util

import java.net.InetAddress
import java.util.concurrent.TimeUnit
import okhttp3.tls.HandshakeCertificates
import okhttp3.tls.HeldCertificate

object MockServerCertificates {
    private val localhost = InetAddress.getByName("localhost").getCanonicalHostName()
    private val localhostCertificate: HeldCertificate = HeldCertificate.Builder()
        .addSubjectAlternativeName(localhost)
        .duration(1, TimeUnit.MINUTES)
        .build()

    val newServerCertificates by lazy {
        val serverCertificates: HandshakeCertificates = HandshakeCertificates.Builder()
            .heldCertificate(localhostCertificate)
            .build()

        serverCertificates
    }

    val newClientCertificates by lazy {
        val clientCertificates: HandshakeCertificates = HandshakeCertificates.Builder()
            .addTrustedCertificate(localhostCertificate.certificate)
            .build()
        clientCertificates
    }
}