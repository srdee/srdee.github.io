data(iris)
fit_species = 
  lm(
    Sepal.Length ~ Species,
    data = iris)
summary(fit_species)

plot(
  Petal.Width ~ Petal.Length,
  data = iris,
  xlab = "Petal Length (cm)",
  ylab = "Petal Width (cm)")

fit_petals = 
  lm(
    Petal.Width ~ Petal.Length,
    data = iris)
summary(fit_petals)

set <- subset(iris, Species == "setosa")
mean(set$Sepal.Length)

virg <- subset(iris, Species == "virginica")
mean(virg$Sepal.Length)