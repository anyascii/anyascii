import Testing
@testable import AnyAscii

@Test func testEmoji() async throws {
    // Write your test here and use APIs like `#expect(...)` to check expected conditions.
    #expect(":grinning:" == AnyAscii.transliterate(from: "😀"))
    #expect(":rofl::upside_down: category - name" == AnyAscii.transliterate(from: "🤣🙃 category - name"))
    
    #expect(":grinning::smiley::smile::grin::face_holding_back_tears::sweat_smile::joy::smiling_face_with_3_hearts::kissing_closed_eyes::stuck_out_tongue_winking_eye:" == AnyAscii.transliterate(from: "😀😃😄😁🥹😅😂🥰😚😜"))
}

@Test func testAscii() async throws {
    #expect("Hello world!" == AnyAscii.transliterate(from: "Hello world!"))
    #expect("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem\n ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?" == AnyAscii.transliterate(from: "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem\n ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?"))
}

@Test func testLatinish() async throws {
    #expect("Combination ueeoa" == AnyAscii.transliterate(from: "Combination üeëøa"))
}

@Test func testForeign() async throws {
    #expect("d+.S" == AnyAscii.transliterate(from: "∂+˚§"))
    #expect("dzntbc" == AnyAscii.transliterate(from: "ǳڃtࢡࢢ"))
}
