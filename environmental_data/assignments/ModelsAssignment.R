require("here")
catrate <- read.csv(here("data","catrate.csv"))
head(catrate)
summary(catrate)
#Build histogram
hist(catrate$cat.rate, xlab="Catastrophe Rate", main="Histogram of Catastrophe Rates")

#Run Shapiro test
shapiro.test(catrate$cat.rate)

#Run t test
t.test(catrate$cat.rate, mu=2/7)

#Run one-sided t test
t.test(catrate$cat.rate, mu=2/7, alternative="greater")

#Wilcox
wilcox.test(catrate$cat.rate, mu = 2 / 7)

penguins -> require(palmerpenguins)
penguin_dat = droplevels(subset(penguins, species != "Gentoo"))
summary(penguin_dat)

boxplot(
  flipper_length_mm ~ species, 
  data = penguin_dat,
  ylab = "Flipper Length (mm)")

dat_adelie = subset(penguins, species == "Adelie")
dat_chinstrap = subset(penguins, species == "Chinstrap")
dat_gentoo = subset(penguins, species == "Gentoo")

