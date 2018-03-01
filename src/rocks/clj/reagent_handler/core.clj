(ns rocks.clj.reagent-handler.core
  (:require [clojure.string :as s]))

(defn to-attribute-value [value]
  (if-not (map? value)
    value
    (->> value
         (map (fn [[k v]]
                (let [v (cond
                          (= :z-index k) v
                          (integer? v)   (str v "px")
                          (keyword? v)   (name v)
                          :default       v)]
                  (format "%s: %s;" (name k) v))))
         (s/join " "))))

(defn conform-attributes [m]
  (->> m
       (map (fn [[k v]]
              [k (to-attribute-value v)]))
       (into {})))

(defn normalize
  "Makes sure all components have opts map as second element."
  [component]
  (if (map? (second component))
    (let [[f m & r] component
          m (conform-attributes m)]
      (into [f m] r))
    (->> (rest component)
         (into [(first component) {}]))))

(defn- render-impl
  "Generates plain hiccup structure from reagent-style hiccup structure."
  ([root component]
   (render-impl root [0] component))
  ([root id component]
   (cond
     (fn? component)
     (render-impl root (component))

     (not (coll? component))
     component

     (coll? (first component))
     (map-indexed #(render-impl false (conj id %1) %2) component)

     (keyword? (first component))
     (let [[tag opts & body] (normalize component)
           opts (cond-> opts
                  root (assoc :data-reactroot true))]
       (->> body
            (map-indexed #(render-impl false (conj id %1) %2))
            (into [tag opts])))

     (fn? (first component))
     (render-impl root id (apply (first component) (rest component))))))

(defn render [component]
  (render-impl true component))
