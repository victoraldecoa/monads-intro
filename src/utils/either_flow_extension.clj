(ns utils.either-flow-extension
  (:require [cats.core :as m]
            [cats.monad.either]
            [fmnoise.flow :as f])
  (:import (cats.monad.either Left Right)))

(extend-protocol f/Flow
  Right
  (?ok [this f] (f (m/extract this)))
  (?err [this _] (m/extract this))
  (?throw [this] (m/extract this))

  Left
  (?ok [this _] (m/extract this))
  (?err [this f] (f (ex-info "Either.Left" (m/extract this))))
  (?throw [this] (throw (ex-info "Either.Left" (m/extract this)))))
