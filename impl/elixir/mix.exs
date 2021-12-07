defmodule AnyAscii.MixProject do
  use Mix.Project

  def project do
    [
      app: :any_ascii,
      version: "0.3.1-dev",
      elixir: "~> 1.12",
      start_permanent: Mix.env() == :prod,
      deps: deps(),
      description: description(),
      package: package(),
      name: "AnyAscii",
      source_url: "https://github.com/anyascii/anyascii"
    ]
  end

  # Run "mix help compile.app" to learn about applications.
  def application do
    [
      extra_applications: [:logger],
      registered: [AnyAscii],
      mod: {AnyAscii, []}
    ]
  end

  # Run "mix help deps" to learn about dependencies.
  defp deps do
    []
  end

  defp description() do
    "Converts Unicode characters to their best ASCII representation"
  end

  defp package() do
    [
      files: ~w(lib mix.exs README* LICENSE*),
      licenses: ["ISC License"]
    ]
  end
end
