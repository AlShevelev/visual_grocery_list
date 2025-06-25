#include <jni.h>
#include <string>
#include <iostream>
#include <vector>
#include <cstdint>
#include <sstream>
#include <iterator>
#include "xxtea.cpp"

std::string getData(int x, const std::string& key, const std::string& data) {
    switch (x) {
        // Encrypting
        case 1: {
            auto encryptedBytes = xxtea::encrypt(data, key);

            std::stringstream result;

            std::copy(
                    encryptedBytes.begin(),
                    encryptedBytes.end(),
                    std::ostream_iterator<std::uint32_t>(result, " ")
            );

            return result.str();
        }

        // Decrypting
        case 2: {
            std::istringstream inputStream(data);

            std::vector<std::uint32_t> bytesToDecrypt{
                    std::istream_iterator<std::uint32_t>(inputStream),
                    std::istream_iterator<std::uint32_t>()
            };

            return xxtea::decrypt(bytesToDecrypt, key);
        }

        default: return "";
    }
}

extern "C" jstring
Java_com_shevelev_visualgrocerylist_network_ApiKeyExtractor_getApiKey(
        JNIEnv *env,
        jobject /* this */,
        jint id,
        jstring source
) {
    std::string key = "!+2hp1Uv8-+P2U)}yZ&rsI^-zHA:d>Q%eM@1AT1:XFKHw@8m,px@~Bp<rXKu4Trh";
    std::string data = env->GetStringUTFChars(source, nullptr);

    std::string result = getData(id, key, data);
    return env->NewStringUTF(result.c_str());
}