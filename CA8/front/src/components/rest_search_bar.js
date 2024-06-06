import React, { useState } from "react";
import "../styles/home.css";
import { postData, sendToast } from "../utils/request_utils";
import { Routes, Route, Link, Navigate, useNavigate } from "react-router-dom";
const page_limit = 12;
const search_general = "/restaurants/search_general";
function SearchBarForm({ restTypes, restLocations }) {
  const navigate = useNavigate();
  const [searchParams, setSearchParams] = useState({page:1,pageLimit:page_limit});

  const handleChange = (event) => {
    console.log("event", searchParams);
    const { name, value } = event.target;
    setSearchParams((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const searchRestaurants = () => {
    postData(
      search_general,
      searchParams,
      (response) => {
        if (response.success) {
          sendToast("true", "Search succesful!");
          navigate("/search_result", { state: {state : response.data , searchParams : searchParams}, replace: true });
        } else {
          sendToast(false, "Search Failed for some reason");
        }
      },
      (res) => {},
      (res) => {}
    );
    return;
  };

  return (
    <div className="search-bars input-group mb-1 text-center w-md-75">
      <select
        className="form-select custom-select rounded-4 search-input"
        id="inputGroupSelect01"
        aria-placeholder="Location"
        name="location"
        onChange={handleChange}
      >
        <option value="" disabled selected>
          Location
        </option>
        {restLocations.map((v) => (
          <option value={v}>{v}</option>
        ))}
      </select>
      <select
        className="form-select custom-select rounded-4 search-input"
        id="RestaurantType"
        width="10px"
        name="type"
        onChange={handleChange}
      >
        <option value="" disabled selected>
          Restaurant
        </option>

        {restTypes.map((v) => (
          <option value={v}>{v}</option>
        ))}
      </select>
      <input
        type="text"
        name="name"
        className="search-input restaurant-search rounded-4"
        placeholder="Type Restaurant ..."
        aria-label="restaurant search"
        onChange={handleChange}
        aria-describedby="basic-addon2"
      />
      <button
        className="btn search-input btn-outline-secondary rounded-4 search-button"
        type="button"
        onClick={searchRestaurants}
      >
        Search
      </button>
    </div>
  );
}

export default SearchBarForm;
