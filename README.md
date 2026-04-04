[![Javadoc](https://img.shields.io/badge/JavaDoc-Online-green)](https://projectmidheaven.github.io/foundation/javadoc/)

This repository is a library that provides the core “foundation” layer  intended as a shared, documented utility toolkit for the Midheaven Project.

The foundation layer contains a broad set of reusable primitives used by other Midheaven Project libraries.

The codebase is organized into focused packages such as

- `org.midheaven.collections` for custom enumerable/sequence abstractions and pipeline-style transformations
- `org.midheaven.lang` for utilities that augment Java SDK own classes
- `org.midheaven.math` for numeric types (such as rational numbers) , intervals, and random generators, 
- `org.midheaven.io` for handling binary content without specifying origin or storage
- `org.midheaven.time` for `java.time.Clock` providers
- with supporting modules for byte content, culture codes, and clock providers. 

It also includes a solid JUnit and ArchUnit test suite, plus published  [JavaDoc](https://projectmidheaven.github.io/foundation/javadoc/).


