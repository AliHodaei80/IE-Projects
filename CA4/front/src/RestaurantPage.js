import "react-toastify/dist/ReactToastify.css";
import "./styles/search_result.css";
import React, { useState, useEffect } from "react";
import Header from "./components/header.js";
import Footer from "./components/footer.js";
import TimeInfoComponent from "./components/time_info.js";
import Rating from "./components/rating.js";
import ReviewCard from "./components/review_card.js";
import ReactPaginate from "react-paginate";
import { isOpen } from "./components/time_info.js";
import "./styles/shared.css";
import "./styles/header.css";
import "./styles/restaurant.css";
import "./styles/search_result.css";
import "./styles/footer.css";
import "./styles/normalize.css";
import "./styles/search_result.css";
import clock_icon from "./images/icons/clock.svg";
import fork_knife from "./images/icons/fork_knife.svg";
import { useParams } from "react-router-dom";
import { fetchData, sendToast } from "./utils/request_utils.js";
import star_inside_review from "./images/icons/star_inside_review.png";
const review_page_size = 5;
export default function RestaurantPage() {
  const [mounted, setMounted] = useState(false);
  const [restaurantData, setRestaurantData] = useState({
    address: {},
    startTime: "12:00:00",
    endTime: "12:00:00",
  });
  const [reviewData, setReviewData] = useState([]);
  const [tableData, setTables] = useState([]);
  const { id } = useParams();

  const [page, setPage] = useState(0);
  const [filteredReview, setFilteredReview] = useState();

  useEffect(() => {
    setFilteredReview(
      reviewData.filter((item, index) => {
        return (
          (index >= page * review_page_size) &
          (index < (page + 1) * review_page_size)
        );
      })
    );
  }, [page]);

  useEffect(() => {
    if (!mounted) {
      setMounted(true);
      fetchData(
        "/restaurants/" + id,
        {},
        (response) => {
          if (response.success) {
            console.log("Rest data", response.data);
            sendToast(true, "Restaurant loaded succesfully");
            setRestaurantData(response.data.restaurant);
            setReviewData(response.data.reviews);
            setFilteredReview(
              response.data.reviews.filter((items, index) => {
                return index <= review_page_size;
              })
            );
            setTables(response.data.restaurantTables);
          } else {
            sendToast(false, "Restaurant fetch failed");
          }
        },
        (res) => {}
      );
    }
  }, [mounted, id]);

  return (
    mounted && (
      <main className="flex-grow-1">
        <Header></Header>
        <div className="mt-0 position-relative ms-0">
          <div className=" align-middle">
            <div className="mt-0 d-flex justify-content-center">
              <div className=" row me-5">
                <div className=" mb-0 col-md ms-0 align-middle w-50">
                  <img
                    src={restaurantData.imageUrl}
                    alt="tables_logo"
                    className="h-50 rounded-4 position-relative col rounded-3 mt-5 w-100"
                  />
                  <div className=" descripton-card mt-5 position-relative">
                    <div className=" d-flex justify-content-between">
                      <p className="display-6 position-absolute mt-5">
                        {restaurantData.name}
                      </p>
                      {isOpen(
                        restaurantData.startTime,
                        restaurantData.endTime
                      ) ? (
                        <p className="rest-status-open rounded-4 text-center position-absolute top-50 end-0 me-2">
                          Open!
                        </p>
                      ) : (
                        <p className="rest-status-closed rounded-4 text-center position-absolute top-50 end-0 me-2">
                          Closed!
                        </p>
                      )}

                      <div className="mb-2 pt-4 mt-1 position-absolute top-100">
                        <div className="">
                          <div className="review-header">
                            <div className="d-flex justify-content-between">
                              <img
                                className="icon p-0"
                                src={clock_icon}
                                alt="star_filled"
                              />

                              <div className="timing p-0">
                                {mounted && restaurantData && (
                                  <TimeInfoComponent
                                    startTime={restaurantData.startTime}
                                    endTime={restaurantData.endTime}
                                  />
                                )}
                              </div>
                            </div>
                            <div className="d-flex align-items-center ms-0">
                              <div className="icon- text-center">
                                <img
                                  className="star-review"
                                  src={star_inside_review}
                                  alt="review"
                                />
                              </div>

                              <div className="rating p-0">
                                {reviewData.length} Reviews
                              </div>
                            </div>
                            <div className="d-flex">
                              <img
                                className="icon"
                                src={fork_knife}
                                alt="star_filled"
                              />
                              <div className="type">{restaurantData.type}</div>
                            </div>
                          </div>
                          <div className="restaurant-location mt-3">
                            <span className="text-muted">
                              {restaurantData.address.country +
                                " " +
                                restaurantData.address.city +
                                " " +
                                restaurantData.address.street}
                            </span>
                          </div>
                          <div className="review-content mt-3 p-1">
                            {restaurantData.description}
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div className=" col-md mt-5 ms-0 p-1 m-0">
                  <h5 id="booking" className="h5">
                    Reserve Table
                  </h5>
                  <div className="d-inline-flex">
                    <span className="text-center mt-1"> For </span>
                    <div className="input-group text-center">
                      <select
                        className="custom-select count-picker rounded-3 ms-2 form-select"
                        id="inputGroupSelect01"
                        aria-placeholder="Location"
                      >
                        <option selected>2</option>
                        <option value="if">3</option>
                      </select>
                      <span className="text-center mt-1 ms-2">
                        {" "}
                        people, on date{" "}
                      </span>
                      <button
                        className="btn date-picker btn-outline-secondary ms-2 search-button"
                        type="button"
                      >
                        <div className="d-flex">
                          <img
                            className="icon p-0"
                            src="../images/icons/calendar.svg"
                            alt="star_filled"
                          />
                          <span className="p-0 ms-3 fw-bolder">
                            {" "}
                            2024-02-18{" "}
                          </span>
                        </div>
                      </button>
                    </div>
                  </div>
                  <br />
                  <br />
                  <span>Available Times for Table #1 (2 seats)</span>
                  <div className=" text-center mt-3 ms-0">
                    <div className="row">
                      <button className="reserve-blob col-sm ms-2 rounded-4 mt-2">
                        11:00 AM
                      </button>
                      <button className="reserve-blob col-sm ms-2 rounded-4 mt-2">
                        12:00PM
                      </button>
                      <button className="reserve-blob col-sm ms-2 rounded-4 mt-2">
                        13:00PM
                      </button>
                      <button className="reserve-blob col-sm ms-2 rounded-4 mt-2">
                        14:00PM
                      </button>
                    </div>
                    <div className="row">
                      <button className="reserve-blob col-sm ms-2 rounded-4 mt-2">
                        15:00PM
                      </button>
                      <button className="reserve-blob col-sm ms-2 rounded-4 mt-2">
                        18:00PM
                      </button>
                      <button className="reserve-blob col-sm ms-2 rounded-4 mt-2">
                        19:00PM
                      </button>
                      <button className="reserve-blob col-sm ms-2 rounded-4 mt-2">
                        20:00PM
                      </button>
                    </div>
                    <div className="general-text w-100 mt-3 mb-3">
                      <p className="fw-bolder red-text">
                        You will reserve this table only for <u>one</u> hour,
                        for more time please contact the restaurant.
                      </p>
                    </div>
                    <div className="row">
                      <button className="red-stylish-button col-sm ms-2 rounded-4">
                        Complete the reservation
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div className="container review-box  w-75 align-middle rounded-2">
              <div className=" mt-2 p-3">
                <div className=" row">
                  <div className="col">
                    <div>
                      <h4>What {reviewData.length} people are saying</h4>
                      <div className="rounded-4 d-flex justify-content-start">
                        <div class="d-flex justify-content-between text-align">
                          <div className="d-flex flex-column">
                            <Rating
                              rate={restaurantData.avgOverallScore}
                            ></Rating>
                          </div>
                          <p className="text-muted ps-0 align-middle mt-1 ms-2">
                            {restaurantData.avgOverallScore} based on recent
                            ratings
                          </p>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="col">
                    <div className="d-flex justify-content-between">
                      <div className="text-center">
                        <p className="mb-0">Food</p>
                        <p className="fw-bolder">
                          {restaurantData.avgFoodScore}
                        </p>
                      </div>
                      <div className="text-center">
                        <p className="mb-0">Service</p>
                        <p className="fw-bolder">
                          {restaurantData.avgServiceScore}
                        </p>
                      </div>
                      <div className="text-center">
                        <p className="mb-0">Ambience</p>
                        <p className="fw-bolder">
                          {restaurantData.avgAmbianceScore}
                        </p>
                      </div>
                      <div className="text-center">
                        <p className="mb-0">Overall</p>
                        <p className="fw-bolder">
                          {restaurantData.avgOverallScore}
                        </p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div className="container review-pages mt-5  align-middle">
            <div className="row">
              <div className="col">
                <div className="review-">
                  <div className=" d-flex justify-content-between align-items-center my-3">
                    <div className="rounded-4">
                      <h4>{reviewData.length} Reviews</h4>
                    </div>
                    <button className="add-review-btn rounded-4 ">
                      Add Review
                    </button>
                  </div>

                  {filteredReview &&
                    filteredReview.map((rD, index) => (
                      <div>
                        <ReviewCard key={index} reviewData={rD} />
                      </div>
                    ))}
                </div>

                <div className="align-bottom">
                  <ReactPaginate
                    className="align-middle d-flex justify-content-center p-5 list-unstyled"
                    containerClassName={"align-middle list-unstyled"}
                    pageClassName={
                      "button page-button align-middle m-2 list-unstyled"
                    }
                    pageLinkClassName="page-link rounded-circle h-100 pt-2 list-unstyled"
                    activeClassName={"border-danger border-2"}
                    onPageChange={(event) => setPage(event.selected)}
                    pageCount={Math.ceil(reviewData.length / review_page_size)}
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
              </div>
            </div>
          </div>
        </div>

        <Footer></Footer>
      </main>
    )
  );
}
