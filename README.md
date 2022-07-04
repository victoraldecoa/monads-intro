# monads-intro

This project demonstrates some usages for [funcool/cats](https://github.com/funcool/cats)
and explains Monads in a practical way, also exemplifying how one could use it in Clojure (or not).

If you want a very quick overview with examples, you can start looking at
[maybe_tutorial.clj](./src/maybe_tutorial.clj).

If you want a slightly deeper dive, the [number-with-logs](./src/number_with_logs) and 
[pet-nickname](./src/pet_nickname) namespaces are a direct translation of the excellent video
[The Absolute Best Intro to Monads For Software Engineers](https://youtu.be/C2w45qRc3aU),
which I totally recommend.

For a Clojure error treatment exercise (using only Either, and drifting away from Monads, honestly), go to 
[client-schema](./src/http_client/client_schema.clj). It mixes all types of patterns, throwing exceptions,
returning exceptions and returning monads and uses [fmnoise/flow](https://github.com/fmnoise/flow)
to treat them gracefully. If it gets too overwhelming, check the other files in the same folder,
which solves the same problem one pattern at a time.

## Project Guide for the Deeper Dive

### tl;dr - ns order:

1. number-with-logs.step-1 to 4
2. pet-nickname.name-nil
3. pet-nickname.name-maybe
4. pet-nickname.name-either
5. pet-nickname.name-errors
6. pet-nickname.name-exceptions

### 1. Number With Logs

Start with `number-with-logs.step-1`, which presents the problem of adding
logs to the result of some math operations. Then, `step-2` solves it in the
simplest way possible, `step-3` presents a solution that implements a monad
using pure Clojure. Lastly, `step-4` is just an extra step introducing a
minor improvement.

### 2. Pet Nickname

Start with `cats.pet-nickname-or-nil`, which introduces the problem and
shows how traditional languages would solve it and why `maybe` monads are
usually an improvement. It also shows how Clojure would solve the problem.
Then, follow to `cats.pet-nickname-or-maybe` to see how to use `cats` to
solve it. Finally, see `cats.pet-nickname-or-errors` and then
`cats.pet-nickname-or-either` to see how one could add error handling to
the feature.
