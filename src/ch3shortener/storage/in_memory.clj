(ns ch3shortener.storage.in-memory
  (:require [ch3shortener.storage :refer :all]))

(defn create-link*
  [!stg id url]
  (when-not (contains? @!stg id)
    (swap! !stg assoc id url)
    id))

(defn get-link*
  [!stg id]
  (get @!stg id))

(defn update-link*
  [!stg id url]
  (when (contains? @!stg id)
    (swap! !stg assoc id url)
    id))

(defn delete-link*
  [!stg id]
  (swap! !stg dissoc id)
  nil)

(defn list-links*
  [!stg]
  @!stg)

(defn in-memory-storage
  []
  (let [!stg (atom {})]
    (reify Storage
      (create-link [_ id url] (create-link* !stg id url))
      (get-link [_ id] (get-link* !stg id))
      (update-link [_ id url] (update-link* !stg id url))
      (delete-link [_ id] (delete-link* !stg id))
      (list-links [_] (list-links* !stg)))))
