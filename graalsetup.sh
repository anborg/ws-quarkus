#export GRAALVM_HOME=/Library/Java/JavaVirtualMachines/graalvm-ce-lts-java11-20.3.1/Contents/Home
xattr -r -d com.apple.quarantine ${GRAALVM_HOME}/../..
gu list
gu install native-image
