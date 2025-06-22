defmodule AnyAscii.MixProject do
  use Mix.Project

  @name "AnyAscii"
  @source_url "https://github.com/anyascii/anyascii"
  @homepage_url "https://anyascii.com"

  def project do
    [
      app: :any_ascii,
      name: @name,
      version: "0.3.3-dev",
      elixir: "~> 1.7",
      deps: [
        {:ex_doc, "~> 0.34", only: :dev, runtime: false}
      ],
      description: "Unicode to ASCII transliteration",
      package: [
        maintainers: ["Hunter WB"],
        licenses: ["ISC"],
        links: %{
          "GitHub" => @source_url,
          "Demo" => @homepage_url
        }
      ],
      source_url: @source_url,
      homepage_url: @homepage_url,
      docs: [
        api_reference: false,
        main: @name,
        source_url_pattern: "#{@source_url}/blob/master/impl/elixir/%{path}#L%{line}"
      ]
    ]
  end

  def application do
    []
  end
end
