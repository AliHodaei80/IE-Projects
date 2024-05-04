import React, { useState } from "react";
import "../styles/home.css";

function SearchBarForm({ restTypes, restLocations }) {
  const [searchParams, setSearchParams] = useState({});
  const handleChange = (event) => {
    console.log("event", event.target.value);
    const { name, value } = event.target;
    setSearchParams((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };
  return (
    <div className="search-bars input-group mb-1 text-center w-md-75">
      <select
        className="form-select custom-select rounded-4 search-input"
        id="inputGroupSelect01"
        aria-placeholder="Location"
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
        className="search-input restaurant-search rounded-4"
        placeholder="Type Restaurant ..."
        aria-label="restaurant search"
        onChange={handleChange}
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
