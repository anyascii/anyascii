call npm test || pause && exit
go test ./go || pause && exit
ruby ruby/lib/any_ascii_test.rb || pause && exit
cd rust && cargo test && cd .. || pause && exit
cd python && python -m pytest && cd .. || pause && exit
cd java && call mvnw test && cd .. || pause && exit
dotnet test csharp/test || pause && exit