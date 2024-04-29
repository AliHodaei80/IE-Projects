import React from "react";
import AboutMizdooni from "./components/about.js";
import SearchBarForm from "./components/searchbar.js";
import Header from "./components/header.js";
import Footer from "./components/footer.js";
export default function Home() {
  return (
    <main className="flex min-h-screen flex-col items-center justify-between p-24">
      <Header />
      <SearchBarForm />
      <AboutMizdooni />
      <Footer />
    </main>
  );
}
