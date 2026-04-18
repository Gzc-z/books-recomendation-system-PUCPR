build:
	@mkdir -p bin
	@javac -d bin -cp "lib/*" $$(find -name "*.java")

run: build
	@java -cp "bin:lib/*" Main

clean:
	@rm -rf bin
