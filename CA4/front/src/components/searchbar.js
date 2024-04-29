import React from "react";
import "../styles/colors.css";
import "../styles/header.css";
import "../styles/bootstrap.min.css";
import "../styles/bootstrap.min.css"
import "../styles/normalize.css"
import "../styles/font.css"
import "../styles/colors.css"
import "../styles/header.css"
import "../styles/shared.css"
import "../styles/home.css" 
import "../styles/search_result.css"
import "../styles/footer.css"

function SearchBarForm() {
  return (
    <div className="search-bars input-group mb-1 text-center w-md-75">
      <select
        className="form-select custom-select rounded-4 search-input"
        id="inputGroupSelect01"
        aria-placeholder="Location"
      >
        <option value="" disabled selected>
          Location
        </option>
        <option value="ff">Tehran</option>
        <option value="tf">Rasht</option>
        <option value="if">Gonabad</option>
      </select>
      <select
        className="form-select custom-select rounded-4 search-input"
        id="RestaurantType"
        width="10px"
      >
        <option value="" disabled selected>
          Restaurant
        </option>
        <option value="ff">Fast Food</option>
        <option value="tf">Traditional Food</option>
        <option value="if">Itallian Food</option>
      </select>
      <input
        type="text"
        className="search-input restaurant-search rounded-4"
        placeholder="Type Restaurant ..."
        aria-label="restaurant search"
        aria-describedby="basic-addon2"
      />
      <button
        className="btn search-input btn-outline-secondary rounded-4 search-button"
        type="button"
      >
        Search
      </button>
    </div>
  );
}

export default SearchBarForm;
