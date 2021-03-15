;; This Source Code Form is subject to the terms of the Mozilla Public
;; License, v. 2.0. If a copy of the MPL was not distributed with this
;; file, You can obtain one at http://mozilla.org/MPL/2.0/.
;;
;; This Source Code Form is "Incompatible With Secondary Licenses", as
;; defined by the Mozilla Public License, v. 2.0.
;;
;; Copyright (c) 2020 UXBOX Labs SL

(ns app.main.ui.workspace.shapes.path
  (:require
   [app.main.data.workspace :as dw]
   [app.main.refs :as refs]
   [app.main.store :as st]
   [app.main.ui.shapes.path :as path]
   [app.main.ui.shapes.shape :refer [shape-container]]
   [app.main.ui.workspace.shapes.path.common :as pc]
   [app.util.dom :as dom]
   [app.util.geom.path :as ugp]
   [rumext.alpha :as mf]))

#_(defn use-double-click [{:keys [id]}]
  (mf/use-callback
   (mf/deps id)
   (fn [event]
     (dom/stop-propagation event)
     (dom/prevent-default event)
     (st/emit! (dw/start-edition-mode id)
               #_(dw/start-path-edit id)))))

(mf/defc path-wrapper
  {::mf/wrap-props false}
  [props]
  (let [shape (unchecked-get props "shape")
        content-modifiers-ref (pc/make-content-modifiers-ref (:id shape))
        content-modifiers (mf/deref content-modifiers-ref)
        editing-id (mf/deref refs/selected-edition)
        editing? (= editing-id (:id shape))
        shape (update shape :content ugp/apply-content-modifiers content-modifiers)]

    [:> shape-container {:shape shape
                         :pointer-events (when editing? "none")}
     [:& path/path-shape {:shape shape
                          :background? true}]]))
