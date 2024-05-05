import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import ReactPaginate from "react-paginate";
import Header from "./components/header.js";
import RestaurantCard from "./components/restaurant_card.js";
import { postData } from "./utils/request_utils.js";
import { sendToast } from "./utils/request_utils.js";
import "./styles/header.css";
import "./styles/search_result.css";
import "./styles/footer.css";
const search_general = "restaurants/search_general";
export default function SearchResultPage() {
  const { state } = useLocation();
  const navigate = useNavigate();
  const pageLimit = 12;

  const handlePageClickForwardBackward = (forward_backward) => {
    const currentPage = state.searchParams.page + (forward_backward ? 1 : -1);
    const newSearchParams = {
      ...state.searchParams,
      page: currentPage,
    };

    postData(
      search_general,
      newSearchParams,
      (response) => {
        if (response.success) {
          sendToast("true", "Search successful!");
          navigate("/search_result", {
            state: { state: response.data, searchParams: newSearchParams },
            replace: true,
          });
        } else {
          sendToast(false, "Search Failed for some reason");
        }
      },
      (res) => {},
      (res) => {}
    );
  };

  
  return (
    <main className="flex-grow-1">
      <Header></Header>
      <div className="p-3 container">
        <div className="search-result-title">Search Results</div>
        <div className="restaurants row p-2 row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 row-cols-xl-5 row-cols-xxl-6 g-4">
          {state &&
          state.state.restaurants.pageList &&
          state.state.restaurants.pageList.length > 0
            ? state.state.restaurants.pageList.map((restaurant, index) => (
                <RestaurantCard key={index} data={restaurant} />
              ))
            : "No results found"}
        </div>
     <button class="btn bg-light rounded-2" onClick={() => {handlePageClickForwardBackward(false)}}>Backward</button>
     <button class="btn bg-light rounded-3" onClick={() => {handlePageClickForwardBackward(true)}}>Next</button>

      </div>
    </main>
  );
}
