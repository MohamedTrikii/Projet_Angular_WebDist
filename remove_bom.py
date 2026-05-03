from pathlib import Path
root = Path(r'backend/src/main/java')
updated = []
for path in root.rglob('*.java'):
    data = path.read_bytes()
    if data.startswith(b'\xef\xbb\xbf'):
        path.write_bytes(data[3:])
        updated.append(str(path))
print('removed_bom_count=', len(updated))
for p in updated:
    print(p)
