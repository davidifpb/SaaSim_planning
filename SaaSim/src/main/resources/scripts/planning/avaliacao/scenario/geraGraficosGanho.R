#/usr/bin/Rscript
library("ggplot2")

on_100_10 <- read.table("on_100_10/profits.dat")$V1
#op_100_10 <- read.table("op_100_10/profits.dat")$V1
#op_100_10 <- read.table("op_partial_100_10/profits.dat")$V1
op_100_10 <- read.table("op_partial_fac5_100_10/profits.dat")$V1
ov_100_10 <- read.table("ov_100_10/profits.dat")$V1
ut_100_10 <- read.table("ut_100_10/profits.dat")$V1

on_100_5 <- read.table("on_100_5/profits.dat")$V1
#op_100_5 <- read.table("op_100_5/profits.dat")$V1
#op_100_5 <- read.table("op_partial_100_5/profits.dat")$V1
op_100_5 <- read.table("op_partial_fac5_100_5/profits.dat")$V1
ov_100_5 <- read.table("ov_100_5/profits.dat")$V1
ut_100_5 <- read.table("ut_100_5/profits.dat")$V1

on_100_15 <- read.table("on_100_15/profits.dat")$V1
#op_100_15 <- read.table("op_100_15/profits.dat")$V1
#op_100_15 <- read.table("op_partial_100_15/profits.dat")$V1
op_100_15 <- read.table("op_partial_fac5_100_15/profits.dat")$V1
ov_100_15 <- read.table("ov_100_15/profits.dat")$V1
ut_100_15 <- read.table("ut_100_15/profits.dat")$V1

on_10_10 <- read.table("on_10_10/profits.dat")$V1
#op_10_10 <- read.table("op_10_10/profits.dat")$V1
#op_10_10 <- read.table("op_partial_10_10/profits.dat")$V1
op_10_10 <- read.table("op_partial_fac5_10_10/profits.dat")$V1
ov_10_10 <- read.table("ov_10_10/profits.dat")$V1
ut_10_10 <- read.table("ut_10_10/profits.dat")$V1

on_10_5 <- read.table("on_10_5/profits.dat")$V1
#op_10_5 <- read.table("op_10_5/profits.dat")$V1
#op_10_5 <- read.table("op_partial_10_5/profits.dat")$V1
op_10_5 <- read.table("op_partial_fac5_10_5/profits.dat")$V1
ov_10_5 <- read.table("ov_10_5/profits.dat")$V1
ut_10_5 <- read.table("ut_10_5/profits.dat")$V1

on_10_15 <- read.table("on_10_15/profits.dat")$V1
#op_10_15 <- read.table("op_10_15/profits.dat")$V1
#op_10_15 <- read.table("op_partial_10_15/profits.dat")$V1
op_10_15 <- read.table("op_partial_fac5_10_15/profits.dat")$V1
ov_10_15 <- read.table("ov_10_15/profits.dat")$V1
ut_10_15 <- read.table("ut_10_15/profits.dat")$V1

on_50_10 <- read.table("on_50_10/profits.dat")$V1
#op_50_10 <- read.table("op_50_10/profits.dat")$V1
#op_50_10 <- read.table("op_partial_50_10/profits.dat")$V1
op_50_10 <- read.table("op_partial_fac5_50_10/profits.dat")$V1
ov_50_10 <- read.table("ov_50_10/profits.dat")$V1
ut_50_10 <- read.table("ut_50_10/profits.dat")$V1

on_50_5 <- read.table("on_50_5/profits.dat")$V1
#op_50_5 <- read.table("op_50_5/profits.dat")$V1
#op_50_5 <- read.table("op_partial_50_5/profits.dat")$V1
op_50_5 <- read.table("op_partial_fac5_50_5/profits.dat")$V1
ov_50_5 <- read.table("ov_50_5/profits.dat")$V1
ut_50_5 <- read.table("ut_50_5/profits.dat")$V1

on_50_15 <- read.table("on_50_15/profits.dat")$V1
#op_50_15 <- read.table("op_50_15/profits.dat")$V1
#op_50_15 <- read.table("op_partial_50_15/profits.dat")$V1
op_50_15 <- read.table("op_partial_fac5_50_15/profits.dat")$V1
ov_50_15 <- read.table("ov_50_15/profits.dat")$V1
ut_50_15 <- read.table("ut_50_15/profits.dat")$V1

#Ganhos para risco de 10%

gop_100_10 <- (op_100_10 - on_100_10) / abs(on_100_10)
gut_100_10 <- (ut_100_10 - on_100_10) / abs(on_100_10)
gov_100_10 <- (ov_100_10 - on_100_10) / abs(on_100_10)

gop_10_10 <- (op_10_10 - on_10_10) / abs(on_10_10)
gut_10_10 <- (ut_10_10 - on_10_10) / abs(on_10_10)
gov_10_10 <- (ov_10_10 - on_10_10) / abs(on_10_10)

gop_50_10 <- (op_50_10 - on_50_10) / abs(on_50_10)
gut_50_10 <- (ut_50_10 - on_50_10) / abs(on_50_10)
gov_50_10 <- (ov_50_10 - on_50_10) / abs(on_50_10)

#Ganhos para risco de 15%

gop_100_15 <- (op_100_15 - on_100_15) / abs(on_100_15)
gut_100_15 <- (ut_100_15 - on_100_15) / abs(on_100_15)
gov_100_15 <- (ov_100_15 - on_100_15) / abs(on_100_15)

gop_10_15 <- (op_10_15 - on_10_15) / abs(on_10_15)
gut_10_15 <- (ut_10_15 - on_10_15) / abs(on_10_15)
gov_10_15 <- (ov_10_15 - on_10_15) / abs(on_10_15)

gop_50_15 <- (op_50_15 - on_50_15) / abs(on_50_15)
gut_50_15 <- (ut_50_15 - on_50_15) / abs(on_50_15)
gov_50_15 <- (ov_50_15 - on_50_15) / abs(on_50_15)

#Ganhos para risco de 5%

gop_100_5 <- (op_100_5 - on_100_5) / abs(on_100_5)
gut_100_5 <- (ut_100_5 - on_100_5) / abs(on_100_5)
gov_100_5 <- (ov_100_5 - on_100_5) / abs(on_100_5)

gop_10_5 <- (op_10_5 - on_10_5) / abs(on_10_5)
gut_10_5 <- (ut_10_5 - on_10_5) / abs(on_10_5)
gov_10_5 <- (ov_10_5 - on_10_5) / abs(on_10_5)

gop_50_5 <- (op_50_5 - on_50_5) / abs(on_50_5)
gut_50_5 <- (ut_50_5 - on_50_5) / abs(on_50_5)
gov_50_5 <- (ov_50_5 - on_50_5) / abs(on_50_5)

#10 usu??rios
jpeg("ganhos_10.jpg")

heuristica <- c("RF", "RF", "RF", "UT", "UT", "UT", "OV", "OV", "OV")
risco <- c(5, 10, 15, 5, 10, 15, 5, 10, 15)
ganho <- c(mean(gop_10_5), mean(gop_10_10), mean(gop_10_15), mean(gut_10_5), mean(gut_10_10), mean(gut_10_15), mean(gov_10_5), mean(gov_10_10), mean(gov_10_15))
interval <- c( 1.96*sd(gop_10_5)/sqrt(length(gop_10_5)), 1.96*sd(gop_10_10)/sqrt(length(gop_10_10)), 1.96*sd(gop_10_15)/sqrt(length(gop_10_15)), 1.96*sd(gut_10_5)/sqrt(length(gut_10_5)), 1.96*sd(gut_10_10)/sqrt(length(gut_10_10)), 1.96*sd(gut_10_15)/sqrt(length(gut_10_15)), 1.96*sd(gov_10_5)/sqrt(length(gov_10_5)), 1.96*sd(gov_10_10)/sqrt(length(gov_10_10)), 1.96*sd(gov_10_15)/sqrt(length(gov_10_15)) )

pd <- position_dodge(.1) # move them .05 to the left and right
dados <- data.frame(heur=heuristica, ris=risco, gan=ganho, ci=interval)

limits <- aes(x=ris, y=100 * gan, ymin=100 * (gan-ci), ymax=100 * (gan+ci), colour=heur, fill=heur, shape=heur)
ggplot(dados, limits) + 
    geom_point(position=pd, size=3) + xlab("Risco de nega????o de servi??o") + ylab("Ganho") + opts(title="Ganho de heur??sticas em rela????o a heur??stica ON") + theme_bw() + scale_y_continuous(breaks=c(-4000, -3000, -2000, -1000, 0, 100, 200, 300, 400)) + scale_x_continuous(breaks=c(0, 5, 10, 15, 20)) + scale_shape_manual(values=c(19, 3, 0)) + geom_errorbar(limits)

dev.off()

#50 usu??rios
jpeg("ganhos_50.jpg")


heuristica <- c("RF", "RF", "RF", "UT", "UT", "UT", "OV", "OV", "OV")
risco <- c(5, 10, 15, 5, 10, 15, 5, 10, 15)
ganho <- c(mean(gop_50_5), mean(gop_50_10), mean(gop_50_15), mean(gut_50_5), mean(gut_50_10), mean(gut_50_15), mean(gov_50_5), mean(gov_50_10), mean(gov_50_15))
interval <- c( 1.96*sd(gop_50_5)/sqrt(length(gop_50_5)), 1.96*sd(gop_50_10)/sqrt(length(gop_50_10)), 1.96*sd(gop_50_15)/sqrt(length(gop_50_15)), 1.96*sd(gut_50_5)/sqrt(length(gut_50_5)), 1.96*sd(gut_50_10)/sqrt(length(gut_50_10)), 1.96*sd(gut_50_15)/sqrt(length(gut_50_15)), 1.96*sd(gov_50_5)/sqrt(length(gov_50_5)), 1.96*sd(gov_50_10)/sqrt(length(gov_50_10)), 1.96*sd(gov_50_15)/sqrt(length(gov_50_15)) )

pd <- position_dodge(.1) # move them .05 to the left and right
dados <- data.frame(heur=heuristica, ris=risco, gan=ganho, ci=interval)

limits <- aes(x=ris, y=100 * gan, ymin=100 * (gan-ci), ymax=100 * (gan+ci), colour=heur, fill=heur, shape=heur)
ggplot(dados, limits) + 
   geom_point(position=pd, size=3) + xlab("Risco de nega????o de servi??o") + ylab("Ganho") + opts(title="Ganho de heur??sticas em rela????o a heur??stica ON") + theme_bw() + scale_y_continuous(breaks=c(-70, -50, -30, -10, 0, 5, 10)) + scale_x_continuous(breaks=c(0, 5, 10, 15, 20)) + scale_shape_manual(values=c(19, 3, 0)) + geom_errorbar(limits)

dev.off()

#100 usu??rios
jpeg("ganhos_100.jpg")


heuristica <- c("RF", "RF", "RF", "UT", "UT", "UT", "OV", "OV", "OV")
risco <- c(5, 10, 15, 5, 10, 15, 5, 10, 15)
ganho <- c(mean(gop_100_5), mean(gop_100_10), mean(gop_100_15), mean(gut_100_5), mean(gut_100_10), mean(gut_100_15), mean(gov_100_5), mean(gov_100_10), mean(gov_100_15))
interval <- c( 1.96*sd(gop_100_5)/sqrt(length(gop_100_5)), 1.96*sd(gop_100_10)/sqrt(length(gop_100_10)), 1.96*sd(gop_100_15)/sqrt(length(gop_100_15)), 1.96*sd(gut_100_5)/sqrt(length(gut_100_5)), 1.96*sd(gut_100_10)/sqrt(length(gut_100_10)), 1.96*sd(gut_100_15)/sqrt(length(gut_100_15)), 1.96*sd(gov_100_5)/sqrt(length(gov_100_5)), 1.96*sd(gov_100_10)/sqrt(length(gov_100_10)), 1.96*sd(gov_100_15)/sqrt(length(gov_100_15)) )

pd <- position_dodge(.1) # move them .05 to the left and right
dados <- data.frame(heur=heuristica, ris=risco, gan=ganho, ci=interval)

limits <- aes(x=ris, y=100 * gan, ymin=100 * (gan-ci), ymax=100 * (gan+ci), colour=heur, fill=heur, shape=heur)
ggplot(dados, limits) + 
    geom_point(position=pd, size=3) + xlab("Risco de nega????o de servi??o") + ylab("Ganho") + opts(title="Ganho de heur??sticas em rela????o a heur??stica ON") + theme_bw() + scale_y_continuous(breaks=c(-60, -50, -40, -30, -20, -10, 0, 1, 2, 4, 5)) + scale_x_continuous(breaks=c(0, 5, 10, 15, 20)) + scale_shape_manual(values=c(19, 3, 0)) + geom_errorbar(limits)
 
dev.off()

