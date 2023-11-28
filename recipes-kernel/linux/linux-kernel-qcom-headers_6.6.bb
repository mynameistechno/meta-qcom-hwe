SECTION = "kernel"

SUMMARY = "Qcom Linux Kernel Headers"
DESCRIPTION = "Installs  kernel headers required to build userspace. \
These headers are installed in ${includedir}/linux-kernel-qcom path."

LICENSE = "GPLv2.0-with-linux-syscall-note"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

inherit kernel-arch

COMPATIBLE_MACHINE = "(qcom)"


SRC_URI = "git://git.codelinaro.org/clo/la/kernel/qcom;protocol=https;branch=kernel.qclinux.1.0.r1-rel;rev=0add36bad2a3adee1c6de4225688d985cc96dfa8"

S = "${WORKDIR}/git"

DEPENDS += "flex-native bison-native rsync-native"

do_configure[noexrec] = "1"
do_compile[noexec] = "1"

do_install () {
    cd ${B}
    headerdir=${B}/headers
    kerneldir=${D}${includedir}/linux-kernel-qcom
    install -d $kerneldir

    # Install all headers inside B and copy only required ones to D
    oe_runmake_call -C ${B} ARCH=${ARCH} headers_install O=$headerdir

    if [ -d $headerdir/include/generated ]; then
        mkdir -p $kerneldir/include/generated/
        cp -fR $headerdir/include/generated/* $kerneldir/include/generated/
    fi

    if [ -d $headerdir/arch/${ARCH}/include/generated ]; then
        mkdir -p $kerneldir/arch/${ARCH}/include/generated/
        cp -fR $headerdir/arch/${ARCH}/include/generated/* $kerneldir/arch/${ARCH}/include/generated/
    fi

    if [ -d $headerdir/${includedir} ]; then
        mkdir -p $kerneldir/${includedir}
        cp -fR $headerdir/${includedir}/* $kerneldir/${includedir}
    fi

    # Remove .install and .cmd files
    find $kerneldir/ -type f -name .install | xargs rm -f
    find $kerneldir/ -type f -name "*.cmd" | xargs rm -f
}

# kernel headers are generally machine specific
PACKAGE_ARCH = "${MACHINE_ARCH}"

# Allow to build empty main package, to include -dev package into the SDK
ALLOW_EMPTY_${PN} = "1"

FILES_${PN}-dev += "linux-kernel-qcom/*"

INHIBIT_DEFAULT_DEPS = "1"
