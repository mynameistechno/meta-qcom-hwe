SUMMARY = "syslog-test example"
SECTION = "examples"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://hello.c"
DEPENDS = "syslog-plumber"

S = "${WORKDIR}"

do_compile() {
    ${CC} hello.c -o hello -lpthread
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 hello ${D}${bindir}
}

TARGET_CC_ARCH += "${LDFLAGS}"
