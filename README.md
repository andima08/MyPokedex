# 📱 MyPokedex

[![Kotlin Version](https://img.shields.io/badge/Kotlin-2.0.21-blue.svg)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Jetpack-Compose-green.svg)](https://developer.android.com/jetpack/compose)
[![Architecture](https://img.shields.io/badge/Architecture-Clean%20DDD-orange.svg)](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

Aplikasi Pokedex modern yang dibangun untuk latihan. Fokus utama proyek ini adalah implementasi **Clean Architecture**, pengelolaan data **Offline-First**, dan antarmuka pengguna yang reaktif menggunakan **Jetpack Compose**.

---

## ✨ Fitur Utama
- **Real-time Search**: Mencari Pokémon dengan fitur *Debounce* (menunda request selama 500ms) untuk mengoptimalkan performa API.
- **Dynamic Pagination**: Menggunakan **Paging 3** untuk pemuatan data list yang lancar dan hemat memori.
- **Offline Mode**: Sinkronisasi otomatis ke **Couchbase Lite (NoSQL)** sehingga data tetap dapat diakses tanpa koneksi internet.
- **Detailed Pokémon Info**: Menampilkan aset visual (Normal & Shiny) serta daftar kemampuan (*abilities*) Pokémon.
- **Modern UI**: Seluruh antarmuka menggunakan **Material 3** dan **Jetpack Compose**.

---

## 🏗️ Arsitektur Proyek (Clean Architecture)
Aplikasi ini menerapkan pemisahan tanggung jawab yang ketat (Separation of Concerns):

1. **Data Layer**: Implementasi API (Retrofit), Repository, dan persistensi lokal (Couchbase).
2. **Domain Layer**: Berisi abstraksi Repository dan *Use Cases* yang murni menggunakan Kotlin (Tanpa ketergantungan framework Android).
3. **UI Layer**: ViewModel menggunakan Coroutines/Flow untuk mengelola State, dan Composable Functions sebagai View.

---

## 🛠️ Tech Stack & Library
- **Bahasa**: Kotlin 2.0.21
- **Networking**: Retrofit 2 & OkHttp (Logging Interceptor)
- **Database**: Couchbase Lite (NoSQL Database)
- **Asynchronous**: Kotlin Coroutines & Flow
- **Image Loading**: Coil Compose
- **Dependency Injection**: Manual Injection dengan Factory Pattern.

---

## ⚙️ Cara Menjalankan Proyek (Instalasi)

Aplikasi ini menggunakan fitur `BuildConfig` untuk menyembunyikan konfigurasi API.

1. **Clone Repositori**:
   ```bash
   git clone [https://github.com/username/mypokedex.git](https://github.com/username/mypokedex.git)
