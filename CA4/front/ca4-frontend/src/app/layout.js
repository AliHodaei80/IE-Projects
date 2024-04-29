import React from 'react';
import { Inter } from "next/font/google";
import "./globals.css";
import Header from './header.js';
import Footer from './footer.js';

const inter = Inter({ subsets: ["latin"] });

export const metadata = {
  title: "Your Website Title",
  description: "Your website description",
};

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <head>
        <meta charSet="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>{metadata.title}</title>
        {/* <style dangerouslySetInnerHTML={{ __html: inter.css }} /> */}
      </head>
      <body className={inter.className}>
        <Header /> {/* Render your Header component */}
        <main>{children}</main>
        <Footer /> {/* Render your Footer component */}
      </body>
    </html>
  );
}
