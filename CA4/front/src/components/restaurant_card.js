import React from "react";
import { Link } from "react-router-dom";
import location_icon from "../images/icons/location.svg";
import dot_icon from "../images/icons/dot.svg";
import Rating from "./rating.js";
import TimeInfoComponent from "./time_info.js";

export default function RestaurantCard({ data }) {
  const overall = data.avgOverallScore;
  const imageUrl = data.imageUrl;
  const city = data.address.city;
  const type = data.type;
  const restName = data.name;

  return (
    <div className="col">
      <div className="restaurant card rounded-4 h-100 position-relative">
        <Link
          to={{
            pathname: "/restaurant",
            state: { overall : overall,imageUrl:imageUrl,type:type,city:city,name:restName },
          }}
          className="text-decoration-none text-reset"
        >
          <Rating rate={overall} />
          <img
            src={imageUrl}
            className="restaurant-img card-img-top object-fit-cover w-100 rounded-top-4"
            alt="rest1_picture"
          />
        </Link>
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
