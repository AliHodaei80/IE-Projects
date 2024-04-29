// Header.js
import React from 'react';
import "./styles/colors.css"
import "./styles/header.css"
import "./styles/bootstrap.min.css"

import Image from 'next/image';

function Header() {
  return (
     <header class="d-flex sticky-top container-fluid" id="header">
      <Image src="/images/logo.png" alt="logo" class="logo" width={100}
          height={10}/>
      <span class="header-text d-none d-sm-block"
        >Reserve Table From Anywhere!</span
      >
      <button class="reserve-button rounded-3 border-0 ms-auto">
        Reserve Now!
      </button>
    </header>
  );
}

export default Header;
