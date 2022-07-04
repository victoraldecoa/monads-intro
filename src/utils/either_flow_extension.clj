(ns utils.either-flow-extension
  (:require [cats.core :as m]
            [cats.monad.either]
            [fmnoise.flow :as f])
  (:import (cats.monad.either Left Right)))

(extend-protocol f/Flow
  Right
  (?ok [this f] (f @this))
  (?err [this _] @this)
  (?throw [this] @this)

  Left
  (?ok [this _] @this)
  (?err [this f] (f (ex-info "Either.Left" @this)))
  (?throw [this] (throw (ex-info "Either.Left" @this))))
