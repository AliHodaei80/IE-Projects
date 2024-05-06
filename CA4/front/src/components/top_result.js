import React, { useEffect, useState } from "react";
import "../styles/search_result.css";
import "../styles/normalize.css";
import "../styles/font.css";
import "../styles/colors.css";
import "../styles/shared.css";
import "../styles/home.css";
import "../styles/search_result.css";
import "../styles/footer.css";

// ------------------------------------------------------------------ //
import RestaurantCard from "./restaurant_card.js";
// ------------------------------------------------------------------ //
const max_top_number_to_show = 6;

function TopResults({ searchResult, title }) {
  return (
    <div className="p-3 container">
      <div className="p-3 container" id="suggestions-container">
        <div className="result-title">{title}</div>
        <div className="restaurants row p-2 row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 row-cols-xl-5 row-cols-xxl-6 g-4">
          {searchResult &&
          searchResult.restaurants &&
          searchResult.restaurants.length > 0
            ? searchResult.restaurants
                .slice(0, max_top_number_to_show)
                .map((restaurant, index) => (
                  <RestaurantCard key={index} data={restaurant} />
                ))
            : null}
        </div>
      </div>
    </div>
  );
}

export default TopResults;
