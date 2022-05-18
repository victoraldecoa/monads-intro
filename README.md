# monads-intro

This project demonstrates some usages for [funcool/cats](https://github.com/funcool/cats)
and explains Monads in a practical way, also exemplifying how one could use it in Clojure (or not).

Most of it is a direct translation of the excelent video [The Absolute Best
Intro to Monads For Software Engineers](https://youtu.be/C2w45qRc3aU), which
I totally recommend.

## tl;dr - ns order:

1. number-with-logs.step-1 to 4
2. cats.pet-nickname-or-nil
3. cats.pet-nickname-or-maybe
4. cats.pet-nickname-or-errors
5. cats.pet-nickname-or-either

## Project Guide

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
