import { defineConfig } from 'vite';
import tailwindcss from '@tailwindcss/vite';

export default defineConfig({
  plugins: [tailwindcss()],
  build: {
    outDir: './css',
    emptyOutDir: false,
    rollupOptions: {
      input: './css/input.css',
      output: {
        assetFileNames: 'output.css',
      },
    },
  },
});
