/**
 * NextUI provider for the app, it will allow the use of
 * React NextUI style components
 */
"use client";

import { NextUIProvider } from "@nextui-org/react";

export function Providers({ children }: { children: React.ReactNode }) {
  return <NextUIProvider>{children}</NextUIProvider>;
}
