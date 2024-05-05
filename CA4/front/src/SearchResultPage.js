import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import ReactPaginate from "react-paginate";
import Header from "./components/header.js";
import RestaurantCard from "./components/restaurant_card.js";
import { postData } from "./utils/request_utils.js";
import { sendToast } from "./utils/request_utils.js";
import { IconContext } from "react-icons";
import { AiFillLeftCircle, AiFillRightCircle } from "react-icons/ai";
import "./styles/header.css";
import "./styles/search_result.css";
import "./styles/footer.css";
import Footer from "./components/footer.js";
// ------------------------------------------------------------------- //
const search_general = "restaurants/search_general";

export default function SearchResultPage() {
  const { state } = useLocation();
  const [page, setPage] = useState(1);
  const [searchData, setSearchData] = useState();
  const pageLimit = 12;
  const handlePageChange = (event) => {
    const currentPage = event.selected;
    const newSearchParams = {
      ...state.searchParams,
      page: currentPage + 1,
    };
    postData(
      search_general,
      newSearchParams,
      (response) => {
        if (response.success) {
          console.log("New Search Result", newSearchParams, response.data);
          setSearchData(response.data);
          sendToast(true, "Switched to Page " + (currentPage + 1));
        } else {
          sendToast(false, "Same Page!");
        }
      },
      (res) => {},
      (res) => {}
    );
    setPage(event.selected);
  };
  useEffect(() => {
    setSearchData(state.state);
  }, []);

  return (
    <main className="justify-content-between ">
      <Header></Header>
      <div className="p-3">
        <div className="search-result-title">Search Results</div>
        <div className="restaurants row p-2 row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 row-cols-xl-5 row-cols-xxl-6 g-4">
          {searchData &&
          searchData.restaurants.pageList &&
          searchData.restaurants.pageList.length > 0
            ? searchData.restaurants.pageList.map((restaurant, index) => (
                <RestaurantCard key={index} data={restaurant} />
              ))
            : "No results found"}
        </div>
      </div>

      <div className="align-bottom">
        <ReactPaginate
          className="align-middle d-flex justify-content-center fixed-bottom p-5"
          containerClassName={"align-middle"}
          pageClassName={"button page-button align-middle m-2"}
          pageLinkClassName="page-link rounded-circle h-100 pt-2"
          activeClassName={"border-danger border-2"}
          onPageChange={handlePageChange}
          pageCount={state.state.restaurants.totalPages}
          breakLabel={null}  
          previousLabel={
            null
            // <IconContext.Provider value={{ color: "#B8C1CC", size: "36px" }}>
            //   <AiFillLeftCircle />
            // </IconContext.Provider>
          }
          nextLabel={
            null
            // <IconContext.Provider value={{ color: "#B8C1CC", size: "36px" }}>
            //   {/* <AiFillRightCircle /> */}
            // </IconContext.Provider>
          }
        />
      </div>
      <Footer />
    </main>
  );
}
