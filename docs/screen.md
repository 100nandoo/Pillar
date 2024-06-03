# Screen
``` mermaid
graph LR
  subgraph Main Tab
    A
    B
    C
    D
  end
  A --> E
  B --> E
  C --> E
  D --> F
  G
  A(Utama)
  B(Artikel)
  E(Artikel Detail)
  C(Cari)
  D(Lainnya)
  F(Tentang)
  G(Generic)
```

| Screen    | API               | Example                                                                                                          |
|-----------|-------------------|------------------------------------------------------------------------------------------------------------------|
| **Utama** | highlight article | [posts?per_page=4](https://buletinpillar.org/wp-json/wp/v2/posts?per_page=4)                                     |
|           | newest articles   | same as above                                                                                                    |
|           | editor choice     | [posts?pilihan=yes&orderby=modified](https://buletinpillar.org/wp-json/wp/v2/posts?pilihan=yes&orderby=modified) |

## Resources
[Figma Prototype](https://www.figma.com/proto/cyX3QMOpe7HDql6BLK9Y1K/Bulletin-Pillar?node-id=60%3A317&starting-point-node-id=60%3A317)