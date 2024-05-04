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
import location_icon from "../images/icons/location.svg";
import dot_icon from "../images/icons/dot.svg";
import rest1 from "../images/rest1.png";
import Rating from "./rating.js";
import TimeInfoComponent from "./time_info.js";
// ------------------------------------------------------------------ //
const max_top_number_to_show = 6;

function SearchResult({ data: data }) {
  const overall = data.avgOverallScore;
  const imageUrl = data.imageUrl;
  const city = data.address.city;
  const type = data.type;
  const restName = data.name;
  return (
    <div className="col">
      <div className="restaurant card rounded-4 h-100 position-relative">
        <a href="#" className="restaurant-link">
          <Rating rate={overall} />
          <img
            src={imageUrl}
            className="restaurant-img card-img-top object-fit-cover w-100 rounded-top-4"
            alt="rest1_picture"
          />
        </a>
        <div className="card-body">
          <div className="card-title name">{restName}</div>
          <div className="review-count card-text">2096 reviews</div>
          <div className="type card-text">{type}</div>

          <div className="card-text d-flex location">
            <img
              className="icon align-self-center"
              src={location_icon}
              alt="location-icon"
            />
            {city}
          </div>
          <div className="card-text d-flex time">
            <div className="open">Open</div>
            <img
              className="icon align-self-center"
              src={dot_icon}
              alt="dot-icon"
            />
            <TimeInfoComponent
              startTime={data.startTime}
              endTime={data.endTime}
            />
          </div>
        </div>
      </div>
    </div>
  );
}

function SearchResults({ searchResult, title }) {
  console.log("Search Data", searchResult);
  const [isMounted, setIsMounted] = useState(false);
  useEffect(() => {
    if (!isMounted) {
      if (searchResult.restaurants === undefined) {
        console.log("Not mounted", searchResult);
        setIsMounted(false);
      } else {
        setIsMounted(true);
      }
    }
  });

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
                  <SearchResult key={index} data={restaurant} />
                ))
            : null}
        </div>
      </div>
    </div>
  );
}

export default SearchResults;
