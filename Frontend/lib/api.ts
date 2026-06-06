// Client API + gestion de session pour Magic VentesStock / Gondor Chic.
// Communique avec le backend Spring Boot (JWT) decrit par les exigences Sprint 2.

const API_URL = process.env.NEXT_PUBLIC_API_URL ?? "http://localhost:8080";

const TOKEN_KEY = "gondorchic.token";
const CLIENT_KEY = "gondorchic.client";

export type Client = {
  id: number;
  prenom: string | null;
  nom: string | null;
  pseudo: string;
};

export type Produit = {
  id: number;
  libelle: string | null;
  prix: number | null;
  quantite: number | null;
  enStock: boolean | null;
  imageLink: string | null;
};

export type Session = {
  token: string;
  client: Client;
};

/** Erreur portant le message renvoye par le backend. */
export class ApiError extends Error {
  status: number;
  constructor(message: string, status: number) {
    super(message);
    this.name = "ApiError";
    this.status = status;
  }
}

/** Authentifie le client et renvoie { client, token }. */
export async function login(pseudo: string, motDePasse: string): Promise<Session> {
  const res = await fetch(`${API_URL}/api/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ pseudo, motDePasse }),
  });

  if (!res.ok) {
    const message = await res.text();
    throw new ApiError(message || "Identifiants de connexion incorrects.", res.status);
  }

  return (await res.json()) as Session;
}

/** Recupere le produit du jour (endpoint protege par JWT). */
export async function getProduitDuJour(token: string): Promise<Produit> {
  const res = await fetch(`${API_URL}/api/produits/du-jour`, {
    headers: { Authorization: `Bearer ${token}` },
  });

  if (!res.ok) {
    const message = await res.text();
    throw new ApiError(message || "Impossible de charger le produit du jour.", res.status);
  }

  return (await res.json()) as Produit;
}

// --- Gestion de session (localStorage) ---

export function saveSession(session: Session): void {
  localStorage.setItem(TOKEN_KEY, session.token);
  localStorage.setItem(CLIENT_KEY, JSON.stringify(session.client));
}

export function getSession(): Session | null {
  if (typeof window === "undefined") return null;
  const token = localStorage.getItem(TOKEN_KEY);
  const clientRaw = localStorage.getItem(CLIENT_KEY);
  if (!token || !clientRaw) return null;
  try {
    return { token, client: JSON.parse(clientRaw) as Client };
  } catch {
    return null;
  }
}

export function clearSession(): void {
  localStorage.removeItem(TOKEN_KEY);
  localStorage.removeItem(CLIENT_KEY);
}
