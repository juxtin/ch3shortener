(ns ch3shortener.storage)

(defprotocol Storage
  (create-link [this id url] "Store the url under the id. Returns the id if successful, nil if the id is already in use.")
  (get-link [this id] "Given an ID, returns the associated URL. Returns nil if there is no associated URL.")
  (update-link [this id new-url] "Updates id to point to new-url. Returns the id if successful, nil if the id has not yet been created.")
  (delete-link [this id] "Removes a link with the given ID from storage, if it exists.")
  (list-links [this] "Returns a map of all known IDs to URLs."))
