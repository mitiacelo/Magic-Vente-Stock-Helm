import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  images: {
    // Les imageLink des produits peuvent etre locaux (/images/*) ou des URLs distantes.
    remotePatterns: [
      { protocol: "https", hostname: "**" },
      { protocol: "http", hostname: "**" },
    ],
  },
};

export default nextConfig;
