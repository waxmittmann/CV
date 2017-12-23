# Cv Json

## Section
```
{
  title: String,
  items: [SectionItem]
}
```

## SectionItem
```
{
  title: String,
  dateSpan: Option[String],
  description: SectionDescription  
}
```

## SectionDescription
### SimpleSectionDescription
```
{
  title: String
}
```
### ElementSectionDescription
```
{
  description: Element
}
```

### WithSubsectionsSectionDescription
```
{
  title: String,
  subsections: List[Subsection]
}
```

## Subsection
```
{
  title: String,
  description: String
)
```

## Element
### Div
#### ElementDiv
```
{
  styles: List[String],
  elems: List[Element]
}
```

#### TextDiv
```
{
  styles: List[String],
  text: String
}
```

### JList
```
{
  styles: List[String],
  items: List[ListItem]
}
```

## ListItem
### ElementListItem
```
{
  styles: List[String],
  elem: Element
}
```

### TextListItem
```
{
  styles: List[String],
  text: String
}
