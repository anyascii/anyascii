#!/bin/sh

set -eux
cd -- "$(dirname -- "${BASH_SOURCE:-$0}")"

cd js && npm test && cd -
cd go && go test && cd -
cd ruby && ruby lib/any_ascii_test.rb && cd -
cd rust && cargo test && cd -
cd python && python3 -m pytest && cd -
cd java && mvn test && cd -
cd csharp && dotnet test && cd -
cd php && php test.php && cd -
cd julia && julia --project=. -e 'using Pkg; Pkg.test()' && cd -
cd sh && ./test.sh && cd -
