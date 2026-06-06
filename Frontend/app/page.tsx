"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { login, saveSession } from "@/lib/api";

export default function Home() {
  const router = useRouter();

  const [pseudo, setPseudo] = useState("Aragorn_II");
  const [motDePasse, setMotDePasse] = useState("anduril");
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    e.stopPropagation();
    setError(null);
    setLoading(true);
    try {
      const session = await login(pseudo, motDePasse);
      saveSession(session);
      router.push("/dashboard");
    } catch {
      setError("Pseudo ou mot de passe incorrect.");
      setLoading(false);
    }
  };

  return (
    <div className="flex flex-1 flex-col bg-background text-foreground">
      <header className="border-b border-border/60">
        <div className="mx-auto flex w-full max-w-7xl items-center justify-between px-6 py-5 text-[11px] tracking-[0.32em] text-muted-foreground font-display uppercase">
          <span className="inline-flex items-center gap-3">
            <span
              aria-hidden
              className="inline-block h-2.5 w-2.5 border border-current"
            />
            Minas Tirith
          </span>
          <span className="hidden sm:inline-flex items-center gap-2">
            Anno IV
            <span className="text-gold">·</span>
            Du Troisième Âge
          </span>
          <span className="inline-flex items-center gap-3">
            Établi 3019
            <span
              aria-hidden
              className="inline-block h-2.5 w-2.5 border border-current"
            />
          </span>
        </div>
      </header>

      <main className="relative flex-1">
        <ArchOrnament />

        <div className="relative z-10 mx-auto flex w-full max-w-7xl flex-col items-center px-6 pt-16 pb-24">
          <div className="flex items-center gap-5">
            <ShieldEmblem />
            <div className="flex flex-col">
              <h1 className="font-display text-4xl tracking-[0.18em] sm:text-5xl">
                <span className="text-foreground">GONDOR</span>{" "}
                <span className="text-gold">CHIC</span>
              </h1>
              <p className="mt-2 font-serif italic text-base text-muted-foreground">
                De Minas Tirith aux quatre royaumes
              </p>
            </div>
          </div>

          <DiamondDivider />

          <p className="font-serif italic text-2xl text-foreground">
            « L&apos;élégance du Gondor. »
          </p>
          <p className="mt-1 text-sm text-muted-foreground">
            Étoffes, parures et atours.
          </p>

          <div className="relative mt-12 w-full max-w-md">
            <span
              aria-hidden
              className="absolute left-1/2 -top-6 z-20 inline-flex h-12 w-12 -translate-x-1/2 items-center justify-center rounded-full bg-gold text-background shadow-md"
            >
              <PlusGlyph />
            </span>

            <Card className="relative overflow-visible rounded-md bg-card px-2 pt-10 pb-8 ring-1 ring-foreground/5 shadow-[0_30px_60px_-30px_rgba(60,40,15,0.25)]">
              <CardHeader className="items-center text-center">
                <CardTitle className="font-display text-sm tracking-[0.32em] uppercase text-foreground">
                  Entrer dans la Citadelle
                </CardTitle>
                <CardDescription className="font-serif italic text-(--gold)/90 text-base">
                  Identifiez-vous pour accéder à votre garde-robe.
                </CardDescription>
              </CardHeader>

              <CardContent>
                <form className="flex flex-col gap-5" onSubmit={handleSubmit}>
                  <div className="flex flex-col gap-2">
                    <Label
                      htmlFor="pseudo"
                      className="font-display text-[11px] tracking-[0.28em] uppercase text-muted-foreground"
                    >
                      Pseudo
                    </Label>
                    <Input
                      id="pseudo"
                      name="pseudo"
                      type="text"
                      autoComplete="username"
                      value={pseudo}
                      onChange={(e) => setPseudo(e.target.value)}
                      className="h-11 rounded-sm border-border/80 bg-background/60 font-serif text-base"
                    />
                  </div>

                  <div className="flex flex-col gap-2">
                    <Label
                      htmlFor="motdepasse"
                      className="font-display text-[11px] tracking-[0.28em] uppercase text-muted-foreground"
                    >
                      Mot de passe
                    </Label>
                    <Input
                      id="motdepasse"
                      name="motdepasse"
                      type="password"
                      autoComplete="current-password"
                      value={motDePasse}
                      onChange={(e) => setMotDePasse(e.target.value)}
                      className="h-11 rounded-sm border-border/80 bg-background/60 font-serif text-base"
                    />
                  </div>

                  {error && (
                    <p className="font-serif italic text-sm text-red-700/90 text-center">
                      {error}
                    </p>
                  )}

                  <button
                    type="submit"
                    disabled={loading}
                    className="mt-4 h-12 w-full rounded-sm bg-primary text-primary-foreground font-display text-xs tracking-[0.32em] uppercase hover:bg-primary/90 disabled:opacity-60"
                  >
                    {loading ? "Identification…" : "S'identifier  →"}
                  </button>
                </form>
              </CardContent>
            </Card>
          </div>
        </div>
      </main>

      <footer className="border-t border-border/60">
        <div className="mx-auto flex w-full max-w-7xl items-center justify-between px-6 py-5 text-[11px] tracking-[0.3em] uppercase text-muted-foreground font-display">
          <span>Gondor Chic</span>
          <nav className="hidden gap-8 sm:flex">
            <a href="#" className="transition-colors hover:text-foreground">
              Mentions légales
            </a>
            <a href="#" className="transition-colors hover:text-foreground">
              Confidentialité
            </a>
            <a href="#" className="transition-colors hover:text-foreground">
              Contact
            </a>
          </nav>
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
      className="h-16 w-14 shrink-0 text-gold"
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

function DiamondDivider() {
  return (
    <div className="my-8 flex w-72 items-center gap-4">
      <span className="h-px flex-1 bg-border" />
      <span
        aria-hidden
        className="block h-2 w-2 rotate-45 bg-gold"
      />
      <span className="h-px flex-1 bg-border" />
    </div>
  );
}

function PlusGlyph() {
  return (
    <svg
      viewBox="0 0 24 24"
      aria-hidden
      className="h-5 w-5"
      fill="none"
      stroke="currentColor"
      strokeWidth="1.6"
      strokeLinecap="round"
    >
      <path d="M12 5 L12 19" />
      <path d="M5 12 L19 12" />
      <path d="M12 3 L12 5" />
      <path d="M12 19 L12 21" />
      <path d="M3 12 L5 12" />
      <path d="M19 12 L21 12" />
    </svg>
  );
}

function ArchOrnament() {
  return (
    <svg
      aria-hidden
      className="pointer-events-none absolute right-0 top-10 hidden h-170 w-130 text-(--gold)/40 lg:block"
      viewBox="0 0 520 680"
      fill="none"
      stroke="currentColor"
      strokeWidth="1"
    >
      <path d="M40 680 L40 240 C40 140 140 60 260 60 C380 60 480 140 480 240 L480 680" />
      <path d="M90 680 L90 250 C90 165 170 95 260 95 C350 95 430 165 430 250 L430 680" />
      <circle cx="260" cy="265" r="78" />
      <circle cx="260" cy="265" r="58" />
      <path d="M40 480 L480 480" strokeDasharray="2 6" />
      <path d="M260 60 L260 95" />
    </svg>
  );
}
