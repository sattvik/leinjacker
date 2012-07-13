# leinjacker

leinjacker is a library of utilities for Leiningen plug-in developers.  Current features include:

1. `leinjacker.eval` gives you an easy way for your project to call `eval-in-project` or `sh` 
   and have it work independent of the version of Leiningen the user is running.

2. `leinjacker.deps` adds some handy functions for querying and manipulating the dependencies 
   of a project.

3. `leinjacker.utils` provides useful utilities for supporting multiple Leiningen generations,
   such as:
   
   * `try-resolve` and `try-resolve-any` - useful for resolving namespaced symbols at
     runtime.
   * `lein-generation` - returns the current Leiningen generation (1 or 2) as an integer.
   * `lein-home` - returns the path to current Leiningen home (typically `~/.lein/`. This
     abstracts away the location of Leiningen's `leiningen-home`, since it moved between
     generations.
   * `read-lein-project` - reads a `project.clj` and returns the project map. This
     abstracts away the location and name of Leiningen's project read function, 
     since it was renamed between generations.
     
## Usage

Just drop the following into your plug-in’s `project.clj`:

````clojure
(defproject lein-tau "6.283"
  :name "The π is a lie!"
  // other stuff
  :dependecies [[leinjacker "0.1.0"]
                // more deps
		])
````

Although this library is built using Leiningen 2, the library itself does not require it.  It should also work with Clojure 1.2 and above.

## Contributing

If you ever find yourself writing the same generic code in more than one of your plug-ins, feel free to send it here.

* Tobias Crawley

## License

Copyright © 2012 Sattvik Software & Technology Resources, Ltd. Co.
All rights reserved.

Distributed under the Eclipse Public License, the same as Clojure.
