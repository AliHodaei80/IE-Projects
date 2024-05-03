import {
  Routes,
  Route,
  Link,
  Navigate,
  useNavigate,
  useLocation,
} from "react-router-dom";
import { fetchData, postData, sendToast } from "./utils/request_utils.js";

import "./styles/home.css";
import logo_big from "./images/logo_big.png";
import background_image from "./images/home.png";
// ------------------------------------------------------------- //
import React, { useState, useEffect } from "react";
// ------------------------------------------------------------- //
import AboutMizdooni from "./components/about.js";
import SearchBarForm from "./components/searchbar.js";
import SearchResult from "./components/search_result.js";
import Header from "./components/header.js";
import Footer from "./components/footer.js";
// ------------------------------------------------------------- //
const search_method_city = "search_by_city";
const search_method_name = "search_by_name";
const search_method_type = "search_by_type";
const user_details_endpoint = "/user/";
const restaurant_search_endpoint = "/restaurants/search";
const all_restaurants = "/restaurants";

// ------------------------------------------------------------- //

export default function Home() {
  const { state } = useLocation();
  const navigate = useNavigate();
  const [userDetails, setUserDetails] = useState({});
  const [isMounted, setIsMounted] = useState(false);
  const [searchResult, setSearchResult] = useState({});
  const [restTypes, setRestTypes] = useState([]);
  const [restLocations, setRestLocations] = useState([]);

  const fetchRestTypes = () => {
    fetchData(
      all_restaurants,
      {},
      (response) => {
        if (response.success) {
          const restTypes = [
            ...new Set(response.data.restaurants.map((val) => val.type)),
          ];
          const restLoactions = [
            ...new Set(
              response.data.restaurants.map((val) => val.address.city)
            ),
          ];
          setRestTypes(restTypes);
          setRestLocations(restLoactions);
        }
      },
      (res) => {}
    );
    return;
  };

  const searchRestaurants = (search_type, key_data) => {
    postData(
      restaurant_search_endpoint,
      {
        action: search_type,
        search: key_data,
      },
      (response) => {
        setSearchResult(response.data);
        sendToast(
          response.success,
          response.success
            ? "Fetched your local restaurants "
            : "Could not show initial restaurants based on your location"
        );
      },
      (res) => {},
      (res) => {}
    );
  };

  const fetchUser = () => {
    fetchData(
      user_details_endpoint + state.username,
      { username: state.username, password: state.password },
      (response) => {
        setUserDetails(response.data);
        if (response.success) {
          searchRestaurants(
            search_method_city,
            response.data.user.address.city
          );
        }
      },
      (res) => {}
    );
  };

  useEffect(() => {
    if (!isMounted) {
      if (state === null) {
        navigate("/authenticate");
        sendToast(false, "Login First!");
      } else {
        fetchRestTypes();
        fetchUser();
      }
      setIsMounted(true);
    } else {
      console.log("Already Fetched user data : ", userDetails);
    }
  });

  return (
    <main className="flex min-h-screen flex-col items-center justify-between p-24">
      <Header />
      <div className="container-s w-100 text-center">
        <div
          className="home-background w-100"
          style={{ backgroundImage: `url(${background_image})` }}
        >
          <div className="container">
            <div className="input-group mb-3">
              <img className="big-logo" src={logo_big} />
            </div>
            <SearchBarForm
              restTypes={restTypes}
              restLocations={restLocations}
            />
          </div>
        </div>
      </div>
      <SearchResult searchResult={searchResult} />
      <AboutMizdooni />
      <Footer />
    </main>
  );
}
