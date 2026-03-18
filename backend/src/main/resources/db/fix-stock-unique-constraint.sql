-- Run this once if adding the same stock code to another wallet fails (e.g. 403/409/500).
-- PostgreSQL: remove unique constraint on code alone so the same code can exist in different wallets.
-- After running, the app uses unique(wallet_id, code) so duplicate code in the SAME wallet is still blocked.

DO $$
DECLARE
  r RECORD;
BEGIN
  FOR r IN (
    SELECT conname FROM pg_constraint
    WHERE conrelid = 'stock'::regclass AND contype = 'u'
      AND array_length(conkey, 1) = 1
      AND (SELECT attname FROM pg_attribute WHERE attrelid = conrelid AND attnum = conkey[1] AND attnum > 0) = 'code'
  ) LOOP
    EXECUTE format('ALTER TABLE stock DROP CONSTRAINT IF EXISTS %I', r.conname);
  END LOOP;
END $$;
