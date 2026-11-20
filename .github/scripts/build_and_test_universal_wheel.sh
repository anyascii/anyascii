#!/usr/bin/env bash
set -euo pipefail

python -m build --wheel --outdir "$RUNNER_TEMP/direct-wheel"

python -m venv "$RUNNER_TEMP/native-venv"
VIRTUAL_ENV="$RUNNER_TEMP/native-venv" maturin develop --release --locked
if python -m build --wheel --outdir "$RUNNER_TEMP/dirty-wheel" > "$RUNNER_TEMP/build.log" 2>&1; then
  exit 1
fi
grep -F 'The source package contains a native extension.' "$RUNNER_TEMP/build.log"

python -m build
for wheel in dist/*.whl "$RUNNER_TEMP"/direct-wheel/*.whl; do
  python -m pip install --force-reinstall --no-deps "$wheel"
  python -I tests/check_wheel.py pure ../../table.tsv
done

python -m pip install --no-deps --target "$RUNNER_TEMP/pure-install" dist/*.whl
PYTHONPATH="$RUNNER_TEMP/pure-install" "$RUNNER_TEMP/native-venv/bin/python" tests/check_wheel.py pure ../../table.tsv
