"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import Image from "next/image";
import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";
import {
  clearSession,
  getProduitDuJour,
  getSession,
  type Client,
  type Produit,
} from "@/lib/api";

const PLACEHOLDER_IMAGE = "/images/cape.jpg";

export default function DashboardPage() {
  const router = useRouter();

  // Lecture paresseuse de la session (localStorage) : null cote serveur, valeur reelle cote client.
  const [client] = useState<Client | null>(() => getSession()?.client ?? null);
  const [produit, setProduit] = useState<Produit | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [quantity, setQuantity] = useState<number>(1);

  useEffect(() => {
    const session = getSession();
    if (!session) {
      router.replace("/");
      return;
    }

    getProduitDuJour(session.token)
      .then((p) => setProduit(p))
      .catch(() => setError("Impossible de charger le produit du jour."))
      .finally(() => setLoading(false));
  }, [router]);

  const handleQuantityChange = (val: number, stock: number) => {
    if (val < 1 || val > stock) return;
    setQuantity(val);
  };

  const addToCart = (name: string) => {
    // Hors perimetre Sprint 2 : l'ajout reel au panier n'est pas implemente.
    alert(`Succès : ${quantity}x "${name}" ajouté(s) à votre besace !`);
  };

  const handleLogout = () => {
    clearSession();
    router.push("/");
  };

  // Modele de vue derive du produit backend (libelle/prix/quantite/imageLink).
  const product = {
    name: produit?.libelle ?? "—",
    price: produit?.prix ?? 0,
    stock: produit?.quantite ?? 0,
    image: produit?.imageLink || PLACEHOLDER_IMAGE,
  };

  const firstName = client?.prenom ?? "";
  const lastName = client?.nom ?? "";

  if (loading) {
    return (
      <div className="flex flex-1 items-center justify-center min-h-screen bg-background font-serif italic text-muted-foreground">
        Chargement…
      </div>
    );
  }

  return (
    <div className="flex flex-1 flex-col bg-background text-foreground min-h-screen">
      <header className="border-b border-border/60">
        <div className="mx-auto flex w-full max-w-7xl items-center justify-between px-6 py-5 text-[11px] tracking-[0.32em] text-muted-foreground font-display uppercase">
          <div className="flex items-center gap-4">
            <ShieldEmblem />
            <div className="flex flex-col header-brand">
              <h1 className="font-display text-2xl tracking-[0.18em] sm:text-3xl leading-none">
                <span className="text-foreground">GONDOR</span>{" "}
                <span className="text-gold">CHIC</span>
              </h1>
              <p className="mt-1 font-serif italic text-xs text-muted-foreground/80 tracking-wide">
                De Minas Tirith aux quatre royaumes
              </p>
            </div>
          </div>

          <div className="flex items-center gap-4 text-right">
            <div className="flex flex-col">
              <span className="font-serif italic text-xs text-gold">Bienvenue,</span>
              <span className="font-serif text-xs text-foreground font-medium">{firstName} {lastName}</span>
            </div>
          </div>
        </div>
      </header>

      <main className="mx-auto w-full max-w-6xl px-6 py-16 flex-1 flex flex-col justify-center">
        
        <div className="grid gap-12 lg:grid-cols-12 items-end">
          
          <div className="lg:col-span-7 flex flex-col gap-8">
            <div>
              <h2 className="font-display text-3xl tracking-wider sm:text-4xl text-foreground">
                Bienvenue, <br />
                <span className="text-gold font-serif italic font-normal">{firstName} {lastName}</span>
              </h2>
              <p className="mt-4 text-sm text-muted-foreground max-w-md leading-relaxed">
                Découvrez notre sélection du jour <br />
                et ajoutez vos articles préférés à votre panier.
              </p>
            </div>

            <Card className="rounded-md bg-card ring-1 ring-foreground/5 shadow-[0_20px_50px_rgba(0,0,0,0.04)] overflow-hidden max-w-md">
              <div className="px-6 pt-5 pb-3 border-b border-border/40 text-center">
                <span className="font-display text-[10px] tracking-[0.3em] uppercase text-gold">
                  Produit du jour
                </span>
              </div>
              
              <CardContent className="p-6 flex flex-col gap-6">
                {error ? (
                  <p className="font-serif italic text-sm text-red-700/90 text-center py-6">
                    {error}
                  </p>
                ) : (
                  <>
                <div className="flex gap-5 items-start">
                  <div className="relative h-24 w-24 bg-muted/40 rounded-sm overflow-hidden shrink-0 border border-border/40 p-1">
                    <Image
                      src={product.image}
                      alt={product.name}
                      fill
                      sizes="96px"
                      className="object-contain"
                    />
                  </div>

                  <div className="flex flex-col gap-1">
                    <h3 className="font-display text-lg tracking-wide text-foreground">
                      {product.name}
                    </h3>
                    <span className="text-xs text-muted-foreground font-serif italic">
                      Collection Terre d&apos;Arnor
                    </span>
                  </div>
                </div>

                <div className="grid grid-cols-2 gap-4 border-t border-b border-border/40 py-4 text-sm">
                  <div className="flex flex-col gap-0.5">
                    <span className="text-[10px] uppercase font-display tracking-wider text-muted-foreground">Prix</span>
                    <span className="font-display text-base text-foreground font-semibold">{product.price} €</span>
                  </div>
                  <div className="flex flex-col gap-0.5 items-end">
                    <span className="text-[10px] uppercase font-display tracking-wider text-muted-foreground">En stock</span>
                    <span className="font-serif text-sm text-emerald-600 font-medium">{product.stock} unités</span>
                  </div>
                </div>

                <div className="flex gap-3 items-center">
                  <div className="flex items-center border border-border rounded-sm bg-background h-11">
                    <button
                      type="button"
                      onClick={() => handleQuantityChange(quantity - 1, product.stock)}
                      className="px-3 h-full text-xs font-bold border-r border-border hover:bg-muted transition-colors"
                    >
                      -
                    </button>
                    <span className="px-3 font-serif text-sm w-10 text-center">
                      {quantity}
                    </span>
                    <button
                      type="button"
                      onClick={() => handleQuantityChange(quantity + 1, product.stock)}
                      className="px-3 h-full text-xs font-bold border-l border-border hover:bg-muted transition-colors"
                    >
                      +
                    </button>
                  </div>

                  <Button
                    onClick={() => addToCart(product.name)}
                    className="flex-1 h-10 rounded-sm bg-[#8c6214] text-white font-display text-[10px] tracking-[0.2em] uppercase hover:bg-[#734f0e] transition-colors flex items-center justify-center gap-2 shadow-none"
                  >
                    <span>Ajouter à la besace</span>
                    <BagIcon />
                  </Button>
                </div>
                  </>
                )}
              </CardContent>
            </Card>
          </div>

          <div className="lg:col-span-5 flex justify-center lg:justify-end">
            <div className="relative w-full max-w-90 aspect-4/5 rounded-t-full border border-border/40 bg-muted/20 p-3 shadow-[0_30px_60px_-15px_rgba(0,0,0,0.08)] flex items-center justify-center overflow-hidden">
              <div className="w-full h-full rounded-t-full overflow-hidden relative">
                <Image
                  src={product.image}
                  alt={product.name}
                  fill
                  sizes="(max-width: 1024px) 100vw, 360px"
                  className="object-cover transition-transform duration-700 hover:scale-105"
                />
              </div>
            </div>
          </div>

        </div>
      </main>

      <footer className="border-t border-border/40 mt-auto bg-background/30 backdrop-blur-sm">
        <div className="mx-auto flex w-full max-w-7xl items-center justify-between px-6 py-6 text-[11px] tracking-[0.25em] uppercase text-stone-700 font-display">
          
          <span className="font-medium opacity-90">Gondor Chic</span>
          
          <div className="hidden sm:flex gap-8 text-stone-600 font-serif italic tracking-normal normal-case text-xs">
            <a href="#" className="hover:text-stone-900 hover:underline underline-offset-4 transition-colors">
              Mentions légales
            </a>
            <a href="#" className="hover:text-stone-900 hover:underline underline-offset-4 transition-colors">
              Confidentialité
            </a>
            <a href="#" className="hover:text-stone-900 hover:underline underline-offset-4 transition-colors">
              Contact
            </a>
          </div>
          
          <button
            type="button"
            onClick={handleLogout}
            className="text-stone-700 hover:text-stone-950 transition-colors font-serif italic lowercase tracking-normal text-xs flex items-center gap-1 border-b border-transparent hover:border-stone-400 pb-0.5"
          >
            ← quitter la citadelle
          </button>

        </div>
      </footer>
    </div>
  );
}

function ShieldEmblem() {
  return (
    <svg
      aria-hidden
      viewBox="0 0 64 80"
      className="h-12 w-10 shrink-0 text-gold"
      fill="none"
      stroke="currentColor"
      strokeWidth="1.2"
    >
      <path d="M4 6 L60 6 L60 40 C60 60 32 76 32 76 C32 76 4 60 4 40 Z" />
      <path d="M32 22 L32 52" />
      <path d="M32 22 C26 28 26 36 32 42 C38 36 38 28 32 22 Z" />
      <path d="M28 52 L36 52" />
    </svg>
  );
}

function BagIcon() {
  return (
    <svg
      viewBox="0 0 24 24"
      aria-hidden
      className="h-4 w-4 opacity-80"
      fill="none"
      stroke="currentColor"
      strokeWidth="1.5"
      strokeLinecap="round"
      strokeLinejoin="round"
    >
      <path d="M6 2L3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4z" />
      <line x1="3" y1="6" x2="21" y2="6" />
      <path d="M16 10a4 4 0 0 1-8 0" />
    </svg>
  );
}