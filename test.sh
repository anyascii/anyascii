#!/bin/sh

set -eux

cd js && npm test && cd -
go test ./go
ruby ruby/lib/any_ascii_test.rb
cd rust && cargo test && cd -
cd python && python -m pytest && cd -
cd java && ./mvnw test && cd -
dotnet test csharp/test
php php/test.php
