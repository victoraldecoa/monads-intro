(ns utils.either-schema
  (:require [cats.monad.either :as either]
            [schema.core :as s]
            [schema.macros :as macros]
            [schema.spec.leaf :as leaf]
            [schema.utils :as utils]))

(defn- precondition
  [s p err-f]
  (fn [ref]
    (when-let [reason (macros/try-catchall (when-not (p @ref) 'not) (catch e# 'throws?))]
      (macros/validation-error s @ref (err-f (utils/value-name @ref)) reason))))

(defrecord Ref [name schema]
  s/Schema
  (spec [this]
    (leaf/leaf-spec
      (precondition
        this
        #(nil? (s/check schema %))
        #(list name schema %))))
  (explain [_] '(name (s/explain schema))))

(defn Either [l r]
  (s/conditional either/left? (Ref. 'Left l)
                 :else (Ref. 'Right r)))
