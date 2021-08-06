#!/bin/sh

set -eux
cd -- "$(dirname -- "${BASH_SOURCE:-$0}")"

cd impl
(cd js && npm test)
(cd go && go test)
(cd ruby && ruby lib/any_ascii_test.rb)
(cd rust && cargo test)
(cd python && python3 -m pytest)
(cd java && mvn test)
(cd csharp && dotnet test)
(cd php && php test.php)
(cd julia && julia --project=. -e 'using Pkg; Pkg.test()')
(cd sh && ./test.sh)
(cd c && ./test.sh)
