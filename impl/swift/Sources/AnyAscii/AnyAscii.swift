// The Swift Programming Language
// https://docs.swift.org/swift-book

import Foundation

public class AnyAscii {
    
    nonisolated(unsafe) private static var cache: [String: [String]] = [:]
    
    public static func transliterate(from input: String) -> String  {
        var result: String = ""
        for scalar in input.unicodeScalars {
            if scalar.isASCII {
                result.append(Character(scalar))
                continue
            }
            let blockNumInt: UInt32 = scalar.value >> 8
            if blockNumInt >= 0xf00 {
                continue
            }
            result.append(getBlockVal(blockNum: String(format:"%03x", blockNumInt), value: Int(scalar.value & 0xff)))
        }
        return result;
    }
    
    private static func getBlockVal(blockNum: String, value: Int) -> String {
        if AnyAscii.cache[blockNum] == nil {
            do {
                let data: Data = try Data(contentsOf: Bundle.module.url(forResource: blockNum, withExtension:"", subdirectory: "Resources")!, options: Data.ReadingOptions.mappedIfSafe)
                var val: String = ""
                var arr: [String] = []
                for uniScalar in data {
                    if uniScalar == 0xff {
                        arr.append(val)
                        val = ""
                        continue
                    }
                    val.append(Character(UnicodeScalar(uniScalar)))
                }
                AnyAscii.cache[blockNum] = arr
            }
            catch {
                return ""
            }
        }
        return AnyAscii.cache[blockNum]![value]
    }
}
